package com.jobinlawrance.weather.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.location.*
import com.jobinlawrance.weather.MyApplication
import com.jobinlawrance.weather.R
import com.jobinlawrance.weather.data.WeatherData
import com.jobinlawrance.weather.ui.dagger.DaggerHomeComponent
import com.jobinlawrance.weather.ui.dagger.PreviousAdapter
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HomeView {

    @Inject
    lateinit var homePresenter: HomePresenter

    @BindView(R.id.location_name)
    lateinit var locationName: TextView
    @BindView(R.id.windspeed)
    lateinit var windSpeed: TextView
    @BindView(R.id.temperature)
    lateinit var temperature: TextView
    @BindView(R.id.previous_layout)
    lateinit var previousLayout: LinearLayout
    @BindView(R.id.recent_recyclerview)
    lateinit var recentRecyclerView: RecyclerView
    @BindView(R.id.previous_message)
    lateinit var previousMessage: TextView

    lateinit var rxPermission: RxPermissions
    lateinit var rxLocationProvider: ReactiveLocationProvider

    var previousAdapter: PreviousAdapter? = null

    val compositeDisposable = CompositeDisposable()

    private val TAG = "###Home"
    private val REQUEST_CHECK_SETTINGS = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        ButterKnife.bind(this)

        val appComponent = (application as MyApplication).appComponent

        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this)

        rxPermission = RxPermissions(this)
        rxLocationProvider = ReactiveLocationProvider(applicationContext)

    }

    override fun onStart() {
        super.onStart()
        homePresenter.subscribe(this)
        getCurrentLocation()
    }

    override fun onStop() {
        super.onStop()
        homePresenter.unSubscribe()
        compositeDisposable.clear()
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val disposable =
                rxPermission
                        .request(Manifest.permission.ACCESS_FINE_LOCATION)
                        .doOnNext { Log.d(TAG, "Permission is $it") }
                        .flatMap { granted ->
                            if (granted) {
                                val locationRequest =
                                        LocationRequest.create()
                                                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                                                .setInterval(500)

                                val locationSettingRequest =
                                        LocationSettingsRequest.Builder()
                                                .addLocationRequest(locationRequest)
                                                .setAlwaysShow(true)
                                                .build()

                                rxLocationProvider
                                        .checkLocationSettings(locationSettingRequest)
                                        .doOnNext { locationSettingResult ->
                                            val status = locationSettingResult.status
                                            if (status.statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                                                try {
                                                    status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                                                } catch (e: IntentSender.SendIntentException) {
                                                    Log.e(TAG, "Error", e)
                                                }
                                            }
                                        }
                                        .flatMap { rxLocationProvider.getUpdatedLocation(locationRequest) }
                                        .take(1)

                            } else
                                Observable.empty<Location>()
                        }
                        .subscribe({
                            Log.d(TAG, "Got current location - ${it.latitude} ${it.longitude}")
                            homePresenter.getWeatherData(it.latitude, it.longitude)
                        }, {
                            Log.e(TAG, "Error", it)
                        })

        compositeDisposable.add(disposable)
    }

    override fun showEmptyPreviousWeather() {

        previousLayout.visibility = View.GONE
        previousMessage.visibility = View.VISIBLE
    }

    override fun showPreviousWeatherData(weatherList: List<WeatherData>) {

        if (previousAdapter == null) {

            previousAdapter = PreviousAdapter()
            recentRecyclerView.adapter = previousAdapter
            recentRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            previousAdapter!!.updateList(weatherList)

            previousLayout.visibility = View.VISIBLE
            previousMessage.visibility = View.GONE

        } else {
            previousAdapter!!.updateList(weatherList)
        }
    }

    override fun showWeatherData(data: WeatherData) {
        locationName.text = data.locationName
        temperature.text = getString(R.string.temperature, data.temp)
        windSpeed.text = getString(R.string.windspeed, data.windSpeed)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                //Reference: https://developers.google.com/android/reference/com/google/android/gms/location/SettingsApi
                when (resultCode) {
                    Activity.RESULT_OK ->
                        // All required changes were successfully made
                        Log.d(TAG, "User enabled location")
                    Activity.RESULT_CANCELED ->
                        // The user was asked to change settings, but chose not to
                        Log.d(TAG, "User Cancelled enabling location")
                }
            }
        }
    }
}

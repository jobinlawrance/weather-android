package com.jobinlawrance.weather.ui

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
import com.jobinlawrance.weather.MyApplication
import com.jobinlawrance.weather.R
import com.jobinlawrance.weather.data.WeatherData
import com.jobinlawrance.weather.ui.dagger.DaggerHomeComponent
import com.jobinlawrance.weather.ui.dagger.PreviousAdapter
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

    var previousAdapter : PreviousAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        ButterKnife.bind(this)

        val appComponent = (application as MyApplication).appComponent

        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this)
    }

    override fun onStart() {
        super.onStart()
        homePresenter.subscribe(this)

    }

    override fun onStop() {
        super.onStop()
        homePresenter.unSubscribe()
    }

    override fun showEmptyPreviousWeather() {

        previousLayout.visibility = View.GONE
        previousMessage.visibility = View.VISIBLE
    }

    override fun showPreviousWeatherData(weatherList: List<WeatherData>) {

        if(previousAdapter == null){

            previousAdapter = PreviousAdapter()
            recentRecyclerView.adapter = previousAdapter
            recentRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            previousAdapter!!.updateList(weatherList)

            previousLayout.visibility = View.VISIBLE
            previousMessage.visibility = View.GONE

        } else {
            previousAdapter!!.updateList(weatherList)
        }
    }

    override fun showWeatherData(data: WeatherData) {

        locationName.text = data.locationName
        temperature.text = getString(R.string.temperature,data.temp)
        windSpeed.text = getString(R.string.windspeed,data.windSpeed)
    }
}

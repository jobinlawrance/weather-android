package com.jobinlawrance.weather.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jobinlawrance.weather.R
import com.jobinlawrance.weather.data.WeatherData

class HomeActivity : AppCompatActivity(), HomeView {

    lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
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

    }

    override fun showPreviousWeatherData(weatherList: List<WeatherData>) {

    }

    override fun showWeatherData(data: WeatherData) {

    }
}

package com.jobinlawrance.weather.ui

import com.jobinlawrance.weather.data.WeatherData

/**
 * Created by jobinlawrance on 16/12/17.
 */
interface HomeView {
    fun showEmptyPreviousWeather()
    fun showPreviousWeatherData(weatherList: List<WeatherData>)
    fun showWeatherData(data: WeatherData)
}
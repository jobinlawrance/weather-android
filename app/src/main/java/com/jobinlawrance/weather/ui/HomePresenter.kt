package com.jobinlawrance.weather.ui

/**
 * Created by jobinlawrance on 16/12/17.
 */
interface HomePresenter {
    fun subscribe(view: HomeView)
    fun unSubscribe()
    fun getWeatherData(lat: Double, lng: Double)
}
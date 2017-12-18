package com.jobinlawrance.weather.data.network

import com.jobinlawrance.weather.data.network.model.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by jobinlawrance on 16/12/17.
 */
interface WeatherApi {
    @GET("data/2.5/weather?appid=6b43a289b325a4dc60798afb111cff0e&units=metric")
    fun getWeatherForLatLng(@Query("lat") lat: Double,@Query("lon") lng: Double): Observable<WeatherResponse>
}
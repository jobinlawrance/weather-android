package com.jobinlawrance.weather.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.jobinlawrance.weather.data.WeatherData
import io.reactivex.Flowable

/**
 * Created by jobinlawrance on 16/12/17.
 */
@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather ORDER BY id DESC")
    fun getWeatherDataListReverseSorted(): Flowable<List<WeatherData>>

    @Insert
    fun insertWeatherData(weatherData: WeatherData)

}
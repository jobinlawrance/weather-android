package com.jobinlawrance.weather.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by jobinlawrance on 16/12/17.
 */
@Entity(tableName = "weather")
data class WeatherData(
        @PrimaryKey(autoGenerate = true) val id: Int?,
        val lat: Double?,
        val lng: Double?,
        val locationName: String?,
        val temp: Double?,
        val windSpeed: Double?
)
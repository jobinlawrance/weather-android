package com.jobinlawrance.weather.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jobinlawrance.weather.data.WeatherData

/**
 * Created by jobinlawrance on 16/12/17.
 */
@Database(entities = arrayOf(WeatherData::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
}
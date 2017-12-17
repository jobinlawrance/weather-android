package com.jobinlawrance.weather.dagger.modules

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.jobinlawrance.weather.data.database.AppDatabase
import com.jobinlawrance.weather.data.database.WeatherDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by jobinlawrance on 17/12/17.
 */
@Module
class RoomModule {

    @Provides
    @Singleton
    fun getRoomDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java,"weather-database").build()

    @Provides
    @Singleton
    fun getWeatherDao(appDatabase: AppDatabase): WeatherDao =
            appDatabase.getWeatherDao()
}
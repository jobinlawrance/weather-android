package com.jobinlawrance.weather.dagger.components

import com.jobinlawrance.weather.dagger.modules.AppModule
import com.jobinlawrance.weather.dagger.modules.NetModule
import com.jobinlawrance.weather.dagger.modules.RoomModule
import com.jobinlawrance.weather.data.database.WeatherDao
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by jobinlawrance on 16/12/17.
 */
@Singleton
@Component(modules = arrayOf(NetModule::class,AppModule::class,RoomModule::class))
interface AppComponent {
    fun provideRetrofit(): Retrofit
    fun provideWeatherDao(): WeatherDao
}
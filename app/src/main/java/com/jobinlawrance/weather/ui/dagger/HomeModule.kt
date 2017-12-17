package com.jobinlawrance.weather.ui.dagger

import com.jobinlawrance.weather.dagger.PerActivity
import com.jobinlawrance.weather.data.database.WeatherDao
import com.jobinlawrance.weather.data.network.WeatherApi
import com.jobinlawrance.weather.ui.HomePresenter
import com.jobinlawrance.weather.ui.HomePresenterImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by jobinlawrance on 17/12/17.
 */
@Module
class HomeModule {

    @Provides
    @PerActivity
    fun providePresenter(weatherApi: WeatherApi, weatherDao: WeatherDao): HomePresenter =
            HomePresenterImpl(weatherApi,weatherDao)

    @Provides
    @PerActivity
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi{
        return retrofit.create(WeatherApi::class.java)
    }
}
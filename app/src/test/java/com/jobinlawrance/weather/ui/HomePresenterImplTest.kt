package com.jobinlawrance.weather.ui

import com.google.gson.Gson
import com.jobinlawrance.weather.data.WeatherData
import com.jobinlawrance.weather.data.database.WeatherDao
import com.jobinlawrance.weather.data.network.WeatherApi
import com.jobinlawrance.weather.data.network.model.WeatherResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.Mockito.`when` as whenever

/**
 * Created by jobinlawrance on 18/12/17.
 */

class HomePresenterImplTest {

    val mockApi = Mockito.mock(WeatherApi::class.java)
    val mockDao = Mockito.mock(WeatherDao::class.java)
    val mockHomeView = Mockito.mock(HomeView::class.java)

    lateinit var presenter: HomePresenterImpl

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        presenter = HomePresenterImpl(mockApi, mockDao)
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
    }

    @Test
    fun testViewUpdate_withEmptyWeatherData() {

        whenever(mockDao.getWeatherDataListReverseSorted()).thenReturn(Flowable.just(emptyList()))

        presenter.subscribe(mockHomeView)
        verify(mockHomeView).showEmptyPreviousWeather()
        verifyNoMoreInteractions(mockHomeView)
    }

    @Test
    fun testViewUpdate_withSingleWeatherData() {
        val weatherData1 = WeatherData(1, 19.1, 72.2, "Borivali", 26.0, 1.6)
        val weatherDataList = listOf<WeatherData>(weatherData1)

        whenever(mockDao.getWeatherDataListReverseSorted()).thenReturn(Flowable.just(weatherDataList))

        presenter.subscribe(mockHomeView)
        verify(mockHomeView).showEmptyPreviousWeather()
        verify(mockHomeView).showWeatherData(weatherData1)
    }

    @Test
    fun testViewUpdate_withMultipleWeatherData() {
        val weatherData1 = WeatherData(1, 19.1, 72.2, "Borivali", 26.0, 1.6)
        val weatherData2 = WeatherData(2, 19.1, 72.2, "Borivali", 26.0, 1.6)
        val weatherData3 = WeatherData(3, 19.1, 72.2, "Borivali", 26.0, 1.6)

        // list in the reverse order(recent first), the way we are getting from the database
        val weatherList = listOf<WeatherData>(weatherData3, weatherData2, weatherData1)
        whenever(mockDao.getWeatherDataListReverseSorted()).thenReturn(Flowable.just(weatherList))

        presenter.subscribe(mockHomeView)

        //check the latest weather is displayed
        verify(mockHomeView).showWeatherData(weatherData3)

        //check previous weather data list doesn't contain the first(recent) WeatherData
        val previousWeatherList = listOf(weatherData2,weatherData1)
        verify(mockHomeView).showPreviousWeatherData(previousWeatherList)
    }

    @Test
    fun testNetworkResultCallsDaoInsert() {
        val weatherResponseString = "{\"coord\":{\"lon\":72.46,\"lat\":19.16},\"weather\":[{\"id\":711,\"main\":\"Smoke\",\"description\":\"smoke\",\"icon\":\"50n\"}],\"base\":\"stations\",\"main\":{\"temp\":28.41,\"pressure\":1015,\"humidity\":39,\"temp_min\":28,\"temp_max\":29},\"visibility\":3500,\"wind\":{\"speed\":2.6,\"deg\":350},\"clouds\":{\"all\":40},\"dt\":1513602000,\"sys\":{\"type\":1,\"id\":7761,\"message\":0.007,\"country\":\"IN\",\"sunrise\":1513561058,\"sunset\":1513600586},\"id\":1275248,\"name\":\"Borivli\",\"cod\":200}\n"
        val weatherResponse = Gson().fromJson(weatherResponseString,WeatherResponse::class.java)

        val weatherData = WeatherData(null,19.16,72.46,"Borivli",28.41,2.6)

        whenever(mockApi.getWeatherForLatLng(anyDouble(),anyDouble())).thenReturn(Observable.just(weatherResponse))

        presenter.getWeatherData(1.0,1.0)
        verify(mockDao).insertWeatherData(weatherData)
    }

}
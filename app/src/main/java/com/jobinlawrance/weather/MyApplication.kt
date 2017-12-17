package com.jobinlawrance.weather

import android.app.Application
import com.jobinlawrance.weather.dagger.components.AppComponent
import com.jobinlawrance.weather.dagger.components.DaggerAppComponent
import com.jobinlawrance.weather.dagger.modules.AppModule

/**
 * Created by jobinlawrance on 16/12/17.
 */
class MyApplication : Application() {

    lateinit var appComponent: AppComponent
    private set

    override fun onCreate() {
        super.onCreate()
        appComponent =
                DaggerAppComponent
                        .builder()
                        .appModule(AppModule(this))
                        .build()
    }

}
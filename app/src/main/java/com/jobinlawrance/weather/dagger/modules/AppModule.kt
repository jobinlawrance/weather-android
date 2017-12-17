package com.jobinlawrance.weather.dagger.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by jobinlawrance on 17/12/17.
 */
@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

}
package com.jobinlawrance.weather.dagger.components

import com.jobinlawrance.weather.dagger.modules.NetModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by jobinlawrance on 16/12/17.
 */
@Singleton
@Component(modules = arrayOf(NetModule::class))
interface AppComponent
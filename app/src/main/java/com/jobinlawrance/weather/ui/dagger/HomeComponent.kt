package com.jobinlawrance.weather.ui.dagger

import com.jobinlawrance.weather.dagger.PerActivity
import com.jobinlawrance.weather.dagger.components.AppComponent
import com.jobinlawrance.weather.ui.HomeActivity
import dagger.Component

/**
 * Created by jobinlawrance on 17/12/17.
 */
@PerActivity
@Component(modules = arrayOf(HomeModule::class),dependencies = arrayOf(AppComponent::class))
interface HomeComponent {
    fun inject(homeActivity: HomeActivity)
}
package com.jobinlawrance.weather.data.network.model

data class WeatherResponse(
		val dt: Long? = null,
		val coord: Coord? = null,
		val visibility: Double? = null,
		val weather: List<WeatherItem?>? = null,
		val name: String? = null,
		val main: Main? = null,
		val clouds: Clouds? = null,
		val id: Int? = null,
		val sys: Sys? = null,
		val wind: Wind? = null
)

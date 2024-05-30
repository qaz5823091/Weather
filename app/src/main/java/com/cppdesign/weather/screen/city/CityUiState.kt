package com.cppdesign.weather.screen.city

import com.cppdesign.weather.db.weather.ForecastWeather

data class CityUiState(
    val title: String = "",
    val forecastWeathers: List<ForecastWeather> = emptyList()
)
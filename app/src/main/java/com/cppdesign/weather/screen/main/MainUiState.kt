package com.cppdesign.weather.screen.main

import com.cppdesign.weather.db.city.City

data class MainUiState(
    var cities: List<City> = emptyList(),
)
package com.cppdesign.weather.service.format

data class CityResult (
    val records: CityLocations
)

data class CityLocations (
    val locations: List<CityLocationsDetail>
)

data class CityLocationsDetail (
    val locationName: String,
    val location: List<City>
)

data class City (
    val locationName: String,
    val geocode: String,
    val lat: String,
    val lon: String
)
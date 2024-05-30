package com.cppdesign.weather.service.format

data class WeatherResult (
    val records: Locations
)

data class Locations (
    val locations: List<LocationsDetail>
)

data class LocationsDetail (
    val locationName: String,
    val location: List<LocationDetail>
)

data class LocationDetail (
    val locationName: String,
    val weatherElement: List<WeatherElement>
)

data class WeatherElement (
    val elementName: String,
    val description: String,
    val time: List<Time>
)

data class Time (
    val startTime: String,
    val endTime: String,
    val elementValue: List<ElementValue>
)

data class ElementValue (
    val value: String,
    val measures: String
)
package com.cppdesign.weather.db

import com.cppdesign.weather.BuildConfig

object Config {
    const val baseURL = "https://opendata.cwa.gov.tw/api/v1/rest/datastore/"
    const val authorization = BuildConfig.WEATHER_API_KEY
    const val forecastElementName = "MinT,MaxT,T,Wx,WeatherDescription"
    const val currentElementName = "Wx,T"
}
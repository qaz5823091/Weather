package com.cppdesign.weather.service

import com.cppdesign.weather.db.Config
import com.cppdesign.weather.service.format.CityResult
import com.cppdesign.weather.service.format.WeatherResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("F-D0047-091")
    suspend fun getWeatherResult(
        @Query("Authorization") authorization: String,
        @Query("locationName") locationName: String,
        @Query("elementName") elementName: String
    ): WeatherResult

    @GET("F-D0047-091")
    suspend fun getCityResult(
        @Query("Authorization") authorization: String,
        @Query("elementName") elementName: String
    ): CityResult

    @GET("F-D0047-089")
    suspend fun getCurrentWeather(
        @Query("Authorization") authorization: String,
        @Query("elementName") elementName: String,
        @Query("timeFrom") timeFrom: String
    ): WeatherResult


    companion object {
        private var weatherService: WeatherService? = null
        fun getInstance(): WeatherService? {
            if (weatherService == null) {
                weatherService = Retrofit.Builder()
                    .baseUrl(Config.baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherService::class.java)
            }

            return weatherService
        }
    }
}
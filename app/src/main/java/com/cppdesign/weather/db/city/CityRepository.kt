package com.cppdesign.weather.db.city

import com.cppdesign.weather.db.weather.CurrentWeather
import kotlinx.coroutines.flow.Flow

class CityRepository(private val cityDao: CityDao) {
    val isNotFetched = cityDao.getCitiesCount() == 0

    fun getAllCities(): Flow<List<City>> {
        return cityDao.getAllCities()
    }

    fun upsert(city: City) {
        cityDao.upsert(city)
    }

    fun updateCurrentWeather(id: Int, weather: CurrentWeather) {
        cityDao.updateTemperature(id, weather.temperature)
        cityDao.updateDescription(id, weather.description)
    }
}
package com.cppdesign.weather.screen.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cppdesign.weather.db.Config
import com.cppdesign.weather.db.city.City
import com.cppdesign.weather.db.city.CityRepository
import com.cppdesign.weather.db.weather.CurrentWeather
import com.cppdesign.weather.service.WeatherService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(private val cityRepository: CityRepository): ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        fetchCities()
        fetchWeathers()
    }

    private fun fetchCities() {
        viewModelScope.launch {
            if (cityRepository.isNotFetched) {
                val response = WeatherService.getInstance()?.getCityResult(Config.authorization, Config.forecastElementName)

                if (response != null) {
                    response.records.locations[0].location.forEach {city ->
                        cityRepository.upsert(City(null, city.locationName))
                    }
                }
            }

            cityRepository.getAllCities().collect {
                _uiState.value = MainUiState(cities = it)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchWeathers() {
        viewModelScope.launch {
            // map into api specific time format
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val currentTime = LocalDateTime.now().format(formatter)
            WeatherService.getInstance()?.getCurrentWeather(Config.authorization, Config.currentElementName, currentTime)
                ?.let { response ->
                    val locations = response.records.locations[0].location
                    _uiState.value.cities.forEachIndexed { index, city ->
                        city.id?.let {
                            val currentWeather = CurrentWeather(
                                temperature = locations[index].weatherElement[1].time[0].elementValue[0].value.toInt(),
                                description = locations[index].weatherElement[0].time[0].elementValue[0].value
                            )

                            cityRepository.updateCurrentWeather(it, currentWeather)
                        }
                    }
                }
        }
    }

    fun saveCity(city: City) {
        viewModelScope.launch {
            city.isSaved = true
            cityRepository.upsert(city)
            _uiState.value.cities = _uiState.value.cities.filter {
                it.isSaved
            }
        }
    }

    private fun restoreCity(city: City) {
        viewModelScope.launch {
            city.isSaved = false
            cityRepository.upsert(city)
        }
    }

    fun restoreAllCities() {
        _uiState.value.cities.filter {
            it.isSaved
        }.forEach {
            restoreCity(it)
        }

        _uiState.value.cities = _uiState.value.cities.filter {
            it.isSaved
        }
    }
}
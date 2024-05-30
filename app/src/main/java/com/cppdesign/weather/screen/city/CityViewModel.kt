package com.cppdesign.weather.screen.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cppdesign.weather.db.Config
import com.cppdesign.weather.db.weather.ForecastWeather
import com.cppdesign.weather.service.WeatherService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CityViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CityUiState())
    val uiState: StateFlow<CityUiState> = _uiState.asStateFlow()

    fun fetchWeathers(cityName: String) {
        viewModelScope.launch {
            WeatherService.getInstance()?.getWeatherResult(Config.authorization, cityName, Config.forecastElementName)
                ?.let {response ->
                    val weathers = response.records.locations[0].location[0].weatherElement
                    val forecastWeatherResult = List(weathers[0].time.size) { index ->
                        ForecastWeather(
                            id = null,
                            currentTemperature = weathers[0].time[index].elementValue[0].value.toInt(),
                            description = weathers[1].time[index].elementValue[0].value,
                            minTemperature = weathers[2].time[index].elementValue[0].value.toInt(),
                            maxTemperature = weathers[4].time[index].elementValue[0].value.toInt(),
                            startTime = weathers[0].time[index].startTime,
                            endTime = weathers[0].time[index].endTime
                        )
                    }

                    _uiState.value = CityUiState(cityName, forecastWeatherResult)
                }
        }
    }
}
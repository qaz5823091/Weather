package com.cppdesign.weather.screen.city

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cppdesign.weather.R
import com.cppdesign.weather.db.weather.ForecastWeather
import com.cppdesign.weather.screen.SubtitleCard
import com.cppdesign.weather.screen.TitleCard
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CityScreen(navController: NavController, cityName: String, cityViewModel: CityViewModel = koinViewModel()) {
    LaunchedEffect(true) {
        cityViewModel.fetchWeathers(cityName)
    }

    val cityUiState by cityViewModel.uiState.collectAsState()

    val commonModifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp)

    Column(
        modifier = Modifier
            .background(Color(0xffaaadab))
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        TitleCard(textState = cityUiState.title)
        Row(
            horizontalArrangement = Arrangement.Start,
        ) {
            SubtitleCard(textState = stringResource(id = R.string.city_subtitle))
        }
        CityWeatherList(cityUiState.forecastWeathers, commonModifier)
        BackButton(onBack = { navController.popBackStack() })
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CityWeatherList(forecastWeatherList: List<ForecastWeather>, modifier: Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxHeight()
    ) {
        forecastWeatherList.forEach { weather ->
            CityWeatherCard(
                time = weather.startTime,
                status = weather.description,
                minTemperature = weather.minTemperature.toString(),
                maxTemperature = weather.maxTemperature.toString()
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CityWeatherCard(time: String, status: String, minTemperature: String, maxTemperature: String) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateTime = LocalDateTime.parse(time, formatter)
    val interval = when(dateTime.hour) {
        in 0..12 -> "早"
        else -> "晚"
    }
    val date = "${dateTime.monthValue}/${dateTime.dayOfMonth} (${interval})"
    val temperature = "${minTemperature}°C ~ ${maxTemperature}°C"

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp, start = 3.dp, end = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(4f),
                text = date,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.weight(5f),
                text = status,
                fontSize = 20.sp,
                maxLines = 1
            )
            Text(
                modifier = Modifier.weight(4f),
                text = temperature,
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun BackButton(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.BottomEnd),
            containerColor = colorResource(R.color.middle_gray),
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            onClick = {
                onBack()
            }
        ) {
            Icon(
                Icons.Filled.List,
                modifier = Modifier.size(50.dp),
                contentDescription = "back"
            )
        }
    }
}

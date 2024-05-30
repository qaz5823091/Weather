package com.cppdesign.weather

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cppdesign.weather.ui.theme.WeatherTheme
import com.cppdesign.weather.screen.city.CityScreen
import com.cppdesign.weather.screen.main.MainScreen
import com.cppdesign.weather.screen.main.MainViewModel
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by inject()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Navigation()
        }
    }
}

enum class Route {
    Main, City
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    WeatherTheme {
        NavHost(navController = navController, startDestination = Route.Main.name) {
            composable(Route.Main.name) {
                MainScreen(navController)
            }
            composable(Route.City.name + "/{name}") {
                val cityName = it.arguments?.getString("name").toString()
                CityScreen(navController, cityName)
            }
        }
    }
}



package com.cppdesign.weather.screen.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.cppdesign.weather.R
import com.cppdesign.weather.Route
import com.cppdesign.weather.db.city.City
import com.cppdesign.weather.screen.SubtitleCard
import com.cppdesign.weather.screen.TitleCard
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.GlobalContext.get
import org.koin.core.parameter.parametersOf

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = koinViewModel{ parametersOf(get()) }) {
    val uiState by mainViewModel.uiState.collectAsState()

    val commonModifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp)

    Column(
        modifier = Modifier
            .background(colorResource(R.color.middle_gray))
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        TitleCard(textState = stringResource(R.string.main_title))
        Row(
            horizontalArrangement = Arrangement.Start,
        ) {
            SubtitleCard(textState = stringResource(id = R.string.main_subtitle))
        }
        GeneralWeatherList(uiState.cities, commonModifier, navController)
        AddCityButton(commonModifier)
        RemoveButton(onClick = { mainViewModel.restoreAllCities() })
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GeneralWeatherList(cities: List<City>, modifier: Modifier, navController: NavController, mainViewModel: MainViewModel = koinViewModel{ parametersOf(get()) }) {
    mainViewModel.fetchWeathers()

    Column(
        modifier = modifier
    ) {
        cities.filter {
            it.isSaved
        }.forEach {city ->
            GeneralWeatherCard(
                city = city.name,
                status = city.currentWeather?.description.toString(),
                temperature = "${city.currentWeather?.temperature.toString()}°C",
                onClick = {
                    navController.navigate(Route.City.name + "/${city.name}")
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GeneralWeatherCard(city: String, status: String, temperature: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick() },
            ),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                modifier = Modifier.weight(2f),
                text = city,
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            Text(
                modifier = Modifier.weight(1.5f),
                text = status,
                fontSize = 25.sp
            )
            Text(
                modifier = Modifier.weight(3f),
                text = temperature,
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddCityButton(modifier: Modifier) {
    var isDialogVisible by remember { mutableStateOf(false) }

    TextButton(
        modifier = modifier
            .background(Color.LightGray),
        onClick = { isDialogVisible = true }) {
        Row {
            Text(
                text = "+ " + stringResource(R.string.add_city),
                fontSize = 20.sp
            )
        }
    }

    if (isDialogVisible) {
        CityDialog(onDismiss = { isDialogVisible = false })
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CityDialog(onDismiss: () -> Unit, mainViewModel: MainViewModel = koinViewModel{ parametersOf(get()) }) {
    val uiState by mainViewModel.uiState.collectAsState()

    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                CityDialogContent(uiState.cities, mainViewModel, onDismiss)
            }

            Button(
                modifier = Modifier
                    .padding(top = 15.dp, end = 16.dp)
                    .align(Alignment.TopEnd),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                onClick = { onDismiss() }
            ) {
                Icon(Icons.Filled.Close, "close")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDialogContent(cities: List<City>, mainViewModel: MainViewModel, onDismiss: () -> Unit) {
    var searchText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.dialog_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Column {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text(stringResource(id = R.string.dialog_label)) },
                placeholder = { Text(stringResource(id = R.string.dialog_placeholder)) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f, false)
            ) {
                cities.filter { city ->
                    !city.isSaved && city.name.contains(searchText, ignoreCase = false)
                }.forEach { city ->
                    CityCard(city = city.name,
                        onButtonClick = {
                            mainViewModel.saveCity(city)
                        },
                        onDismiss = {
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CityCard(city: String, onButtonClick: () -> Unit, onDismiss: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black, RectangleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = city,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
        )

        Button(
            modifier = Modifier.clip(CircleShape),
            onClick = {
                onButtonClick()
                onDismiss()
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.White
            )
        ) {
            Icon(Icons.Filled.Add, "Add a city")
        }
    }
}

@Composable
fun RemoveButton(onClick: () -> Unit) {
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
                onClick()
            }
        ) {
            Icon(
                Icons.Filled.Delete,
                modifier = Modifier.size(50.dp),
                contentDescription = "remove"
            )
        }
    }
}
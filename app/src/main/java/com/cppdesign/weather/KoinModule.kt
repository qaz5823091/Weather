package com.cppdesign.weather

import com.cppdesign.weather.db.AppDatabase
import com.cppdesign.weather.db.city.CityRepository
import com.cppdesign.weather.screen.main.MainViewModel
import com.cppdesign.weather.screen.city.CityViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppDatabase.getInstance(androidContext()).cityDao() }
    single { CityRepository(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { CityViewModel() }
}
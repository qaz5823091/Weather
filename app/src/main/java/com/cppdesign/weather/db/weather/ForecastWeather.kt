package com.cppdesign.weather.db.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForecastWeather(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "min_temperature")
    var minTemperature: Int,

    @ColumnInfo(name = "max_temperature")
    var maxTemperature: Int,

    @ColumnInfo(name = "current_temperature")
    var currentTemperature: Int,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "start_time")
    var startTime: String,

    @ColumnInfo(name = "end_time")
    var endTime: String
)
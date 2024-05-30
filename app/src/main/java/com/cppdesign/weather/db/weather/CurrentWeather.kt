package com.cppdesign.weather.db.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "current_weathers",
)
data class CurrentWeather(
    @PrimaryKey(autoGenerate = true)
    var weatherId: Int? = null,

    @ColumnInfo("temperature")
    var temperature: Int,

    @ColumnInfo("description")
    var description: String
)
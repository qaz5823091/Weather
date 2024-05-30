package com.cppdesign.weather.db.city

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cppdesign.weather.db.weather.CurrentWeather

@Entity(tableName = "cities")
data class City(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "name")
    val name: String,

    @Embedded
    var currentWeather: CurrentWeather? = null,

    @ColumnInfo(name = "is_saved")
    var isSaved: Boolean = false
)
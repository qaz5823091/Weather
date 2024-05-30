package com.cppdesign.weather.db.city

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Query("SELECT * FROM cities")
    fun getAllCities(): Flow<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(city: City)

    @Query("UPDATE cities SET temperature = :temperature WHERE id = :id")
    fun updateTemperature(id: Int, temperature: Int)

    @Query("UPDATE cities SET description = :description WHERE id = :id")
    fun updateDescription(id: Int, description: String)

    @Query("DELETE FROM cities")
    suspend fun removeAllCities()

    @Query("SELECT COUNT(*) FROM cities")
    fun getCitiesCount(): Int
}
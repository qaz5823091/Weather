package com.cppdesign.weather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cppdesign.weather.db.city.City
import com.cppdesign.weather.db.city.CityDao

@Database(entities = [City::class], version = 6)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this){
                val roomDatabaseInstance = Room.databaseBuilder(context, AppDatabase::class.java,"weather-database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = roomDatabaseInstance

                return roomDatabaseInstance
            }
        }
    }
}
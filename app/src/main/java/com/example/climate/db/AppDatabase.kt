package com.example.climate.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.climate.model.Coord

@Database(entities = arrayOf(Coord::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coord(): CoordDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null)
            //Creates a database object with help of ROOM by name of WeatherDB
                instance = Room.databaseBuilder(
                    context, AppDatabase::class.java,
                    "WeatherDB"
                ).build()

            return instance!!
        }
    }
}
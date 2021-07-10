package com.example.climate.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.climate.model.City
import com.example.climate.model.Coord

@Dao
interface CityDao {
    @Query("SELECT * FROM City")
    fun getCityList(): List<City>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityDetail: City)
}
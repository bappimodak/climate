package com.example.climate.repository

import com.example.climate.db.CityDao
import com.example.climate.model.City

class CityRepository(private val cityDao: CityDao) {
    suspend fun insertCoord(city: City){
        cityDao.insert(city)
    }

    suspend fun getCityList(): List<City>?{
        return cityDao.getCityList()
    }
}
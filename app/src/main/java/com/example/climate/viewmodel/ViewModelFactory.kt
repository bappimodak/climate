package com.example.climate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.climate.repository.CityRepository
import com.example.climate.db.CityDao

class ViewModelFactory(private val cityDao: CityDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(cityRepository = CityRepository(cityDao)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
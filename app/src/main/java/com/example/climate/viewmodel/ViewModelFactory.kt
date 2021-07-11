package com.example.climate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.climate.repository.CityRepository
import com.example.climate.db.CityDao
import com.example.climate.network.APIService
import com.example.climate.repository.WeatherRepository

class ViewModelFactory(private val cityDao: CityDao, private val apiService: APIService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(cityRepository = CityRepository(cityDao), WeatherRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
package com.example.climate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.climate.repository.CoordRepository
import com.example.climate.db.CoordDao

class ViewModelFactory(private val coordDao: CoordDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(coordRepository = CoordRepository(coordDao)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
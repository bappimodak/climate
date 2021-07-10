package com.example.climate.viewmodel

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climate.model.City
import com.example.climate.repository.CityRepository
import com.example.climate.utils.LocationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(private val cityRepository: CityRepository) : ViewModel() {
    lateinit var location: Location

    var cityList = MutableLiveData<List<City>>().apply { value = null }

    fun getLastKnownLocation(context: Context, locationHelper: LocationHelper) {
        locationHelper.fetchLastKnownLocation {
            Log.e("MainActivity", "Last known location: $it")
            if (it != null) {
                location = it
            }
        }
    }

    fun getCityList() {
        viewModelScope.launch(Dispatchers.IO) {
            cityList.postValue(cityRepository.getCityList())
        }
    }

    fun storeLocationInDB(cityAddress: City) {
        viewModelScope.launch(Dispatchers.IO) {
            cityRepository.insertCoord(cityAddress)
        }
    }
}
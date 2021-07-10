package com.example.climate.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climate.utils.LocationHelper
import com.example.climate.model.Coord
import com.example.climate.repository.CoordRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(private val coordRepository: CoordRepository): ViewModel() {
    lateinit var location: LatLng

    fun getLastKnownLocation(context: Context, locationHelper: LocationHelper){
        locationHelper.fetchLastKnownLocation(context) {
            Log.e("HomeViewModel", "Last known location: $it")
            if (it != null) {
                location = LatLng(it.latitude, it.longitude)
            }
        }
    }

    fun storeLocationInDB(coord: Coord) {
        viewModelScope.launch(Dispatchers.IO) {
            coordRepository.insertCoord(coord)
        }
    }
}
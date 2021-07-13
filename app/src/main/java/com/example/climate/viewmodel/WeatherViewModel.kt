package com.example.climate.viewmodel

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climate.model.City
import com.example.climate.model.Weather
import com.example.climate.repository.CityRepository
import com.example.climate.repository.WeatherRepository
import com.example.climate.utils.LocationHelper
import com.example.climate.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    lateinit var location: Location

    var cityList = MutableLiveData<Resource<List<City>>>()/*.apply { value = null }*/
    var weather = MutableLiveData<Resource<Weather>>()/*.apply { value = null }*/

    fun getLastKnownLocation(context: Context, locationHelper: LocationHelper) {
        viewModelScope.launch(Dispatchers.Default) {
            locationHelper.fetchLastKnownLocation {
                Log.e("MainActivity", "Last known location: $it")
                if (it != null) {
                    location = it
                }
            }
        }
    }

    fun getCityList() {
        viewModelScope.launch(Dispatchers.IO) {
            cityList.postValue(Resource.loading(null))
            try {
                val response = cityRepository.getCityList()
                cityList.postValue(Resource.success(response))
            } catch (e: Exception) {
                cityList.postValue(Resource.error(e.toString(), null))
            }
//            cityList.postValue(cityRepository.getCityList())
        }
    }

    fun storeLocationInDB(cityAddress: City) {
        viewModelScope.launch(Dispatchers.IO) {
            cityRepository.insertCoord(cityAddress)
        }
    }

    fun getCityWeather(location: Location) {
        viewModelScope.launch(Dispatchers.Default) {

            weather.postValue(Resource.loading(null))
            try {
                val w: Weather = weatherRepository.fetchWeather(location)
                weather.postValue(Resource.success(w))
            } catch (e: Exception) {
                weather.postValue(Resource.error(e.toString(), null))
            }
//            Log.d("WeatherViewModel", w.toString())
//            withContext(Dispatchers.Main){
//                weather.value = w
//            }
            Log.d("WeatherViewModel", weather.value.toString())

        }
    }
}
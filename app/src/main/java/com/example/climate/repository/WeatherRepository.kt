package com.example.climate.repository

import android.location.Location
import android.util.Log
import com.example.climate.model.Weather
import com.example.climate.network.APIService

class WeatherRepository(private val apiService: APIService) {
    /**
     *
     * Fetches weather from server is there is active internet connection
     * If there is no active internet connection, feches weather from local db
     */
    suspend fun fetchWeather(location: Location): Weather {
        val weather = apiService.getCurrentWeather(
            location.latitude.toString(),
            location.longitude.toString(),
            "78b292fab5d6d6f990851ce33660612b"
        )
        Log.d("APICALL", weather.toString())
        return weather
    }
}
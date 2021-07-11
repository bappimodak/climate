package com.example.climate.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "http://api.openweathermap.org/"
    private const val APP_ID = "fae7190d7e6433ec3a45285ffcf55c86"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().
            baseUrl(BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).
            build()
    }

    val apiService: APIService = getRetrofit().create(APIService::class.java)
}
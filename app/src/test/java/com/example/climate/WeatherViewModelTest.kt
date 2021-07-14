package com.example.climate

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.climate.model.City
import com.example.climate.model.Weather
import com.example.climate.repository.CityRepository
import com.example.climate.repository.WeatherRepository
import com.example.climate.utils.Resource
import com.example.climate.utils.TestCoroutineRule
import com.example.climate.viewmodel.WeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var cityRepository: CityRepository

    @Mock
    private lateinit var weatherRepository: WeatherRepository

    @Mock
    private lateinit var location: Location

//    @Mock
//    private lateinit var databaseHelper: DatabaseHelper

    @Mock
    private lateinit var apiWeatherObserver: Observer<Resource<Weather>>

    @Mock
    private lateinit var cityListObserver: Observer<Resource<List<City>>>

    @Before
    fun setUp() {
        // do something if required
    }

    @Test
    fun fetchWeather_shouldReturnSuccess() {
        val weather = Weather(City("Test City"))
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(weather)
                .`when`(weatherRepository)
                .fetchWeather(location)
            val viewModel = WeatherViewModel(cityRepository, weatherRepository)
            viewModel.getCityWeather(location)
            viewModel.weather.observeForever(apiWeatherObserver)
            Mockito.verify(weatherRepository).fetchWeather(location)
            Mockito.verify(apiWeatherObserver).onChanged(Resource.success(weather))
            viewModel.weather.removeObserver(apiWeatherObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        val weather = Weather(City("Test City"))
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(weatherRepository)
                .fetchWeather(location)
            val viewModel = WeatherViewModel(cityRepository, weatherRepository)
            viewModel.getCityWeather(location)
            viewModel.weather.observeForever(apiWeatherObserver)
            Mockito.verify(weatherRepository).fetchWeather(location)
            Mockito.verify(apiWeatherObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.weather.removeObserver(apiWeatherObserver)
        }
    }

    @Test
    fun fetchCityList_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(emptyList<City>())
                .`when`(cityRepository)
                .getCityList()
            val viewModel = WeatherViewModel(cityRepository, weatherRepository)
            viewModel.getCityList()
            viewModel.cityList.observeForever(cityListObserver)
            Mockito.verify(cityRepository).getCityList()
            Mockito.verify(cityListObserver).onChanged(Resource.success(emptyList<City>()))
            viewModel.cityList.removeObserver(cityListObserver)
        }
    }


    @Test
    fun error_whenFetchCityList_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(cityRepository)
                .getCityList()
            val viewModel = WeatherViewModel(cityRepository, weatherRepository)
            viewModel.getCityList()
            viewModel.cityList.observeForever(cityListObserver)
            Mockito.verify(cityRepository).getCityList()
            Mockito.verify(cityListObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.cityList.removeObserver(cityListObserver)
        }
    }


    @After
    fun tearDown() {
        // do something if required
    }
}
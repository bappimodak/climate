package com.example.climate.utils

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.*
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


/**
 * Helper class to access device last known location
 */
class LocationHelper(private val context: Context) {
    val geocoder: Geocoder = Geocoder(context, Locale.getDefault())
    /**
     * Get last known location of a device
     */
    fun fetchLastKnownLocation(context: Context, onDone: (Location?) -> Unit) {
        // Fused location provider
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw SecurityException("App requires location permission")
        }

        val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            GlobalScope.launch(Dispatchers.Main) {
                onDone(location)
            }
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                onDone(location)
            }
        }
    }
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fun fetchLastKnownLocation(onDone: (Location?) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw SecurityException("App requires location permission")
        }
        val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            GlobalScope.launch(Dispatchers.Main) {
                onDone(location)
            }
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                onDone(location)
            }
        }
    }

    /**
     * Get addresses from location
     */
    fun getAddress(location: Location, maxResults: Int = 1 ): List<Address>? {
        return geocoder.getFromLocation(location.latitude, location.longitude, maxResults).toList()
    }
}
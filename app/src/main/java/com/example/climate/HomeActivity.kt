package com.example.climate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity()/*, OnMapReadyCallback */{

//    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//                .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
        addFragmentOnTop(HomeFragment.newInstance())
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        mMap.setOnMapClickListener {
//            mMap.clear()
//            mMap.addMarker(MarkerOptions().position(it).title("Marker in Sydney"))
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
//        }
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//    }

    /**
     * Add a fragment on top of the current stack
     */
    fun addFragmentOnTop(fragment: Fragment?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment!!)
            .addToBackStack(null)
            .commit()
    }

}
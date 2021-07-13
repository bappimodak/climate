package com.example.climate.ui

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climate.R
import com.example.climate.db.AppDatabase
import com.example.climate.model.City
import com.example.climate.network.RetrofitBuilder
import com.example.climate.utils.LocationHelper
import com.example.climate.utils.Status
import com.example.climate.viewmodel.ViewModelFactory
import com.example.climate.viewmodel.WeatherViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(){
    private lateinit var locationHelper: LocationHelper
    private lateinit var viewModel: WeatherViewModel
    private lateinit var cityAdapter: CityAdapter

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val currentLocation = LatLng(viewModel.location.latitude, viewModel.location.longitude)

        googleMap.addMarker(MarkerOptions().position(currentLocation).title("Current Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))

        googleMap.setOnMapClickListener {
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(it))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            viewModel.location.latitude = it.latitude
            viewModel.location.longitude = it.longitude

            val address = locationHelper.getAddress(viewModel.location)
            val cityAddress = address?.get(0)?.getAddressLine(0)

            viewModel.storeLocationInDB(
                City(lat = it.latitude, lon = it.longitude, cityName = cityAddress ?: "")
            )

            Toast.makeText(
                activity as HomeActivity,
                getString(R.string.location_added),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.getCityList()
            changeUI()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        activity?.title = "Bookmarked Cities";
        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationHelper = LocationHelper(requireContext())

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(AppDatabase.getInstance(requireActivity()).coord(), RetrofitBuilder.apiService)
        ).get(WeatherViewModel::class.java)

        viewModel.getCityList()
        viewModel.getLastKnownLocation(requireActivity(), locationHelper)


        setAdapter()
        addObserver()

        fab.setOnClickListener {
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
            map.visibility = View.VISIBLE
        }
    }

    private fun addObserver() {
//        viewModel.cityList.observe(viewLifecycleOwner, { list ->
//            if (list != null) {
//                cityAdapter.list = list
//                cityAdapter.notifyDataSetChanged()
//                controlListUI()
//            }
//        })
        viewModel.cityList.observe(requireActivity(), Observer {
            it.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        resource.data?.let { list ->
                            cityAdapter.list = list
                            cityAdapter.notifyDataSetChanged()
                            controlListUI()
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
//                        recyclerView.visibility = View.GONE
//                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun controlListUI() {
        if (cityAdapter.list.size == 0) {
            cityRecyclerView.visibility = View.GONE
            noItemAlertTV.visibility = View.VISIBLE
        } else {
            cityRecyclerView.visibility = View.VISIBLE
            noItemAlertTV.visibility = View.GONE
        }
    }

    private fun changeUI() {
        map.visibility = View.GONE
    }


    private fun setAdapter() {
        cityAdapter = CityAdapter(requireActivity(), object : CityAdapter.CallBack{
            override fun cityOnClick(city: City) {
                viewModel.location.latitude = city.lat!!
                viewModel.location.longitude = city.lon!!
                (activity as HomeActivity).addFragmentOnTop(DetailFragment.newInstance())
            }

        })
        cityRecyclerView.adapter = cityAdapter
        cityRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        cityRecyclerView.addItemDecoration(
            DividerItemDecoration(
                cityRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    companion object {
        fun newInstance(): Fragment {
            return HomeFragment()
        }
    }
}
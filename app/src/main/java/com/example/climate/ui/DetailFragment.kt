package com.example.climate.ui

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.climate.BR
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.climate.R
import com.example.climate.db.AppDatabase
import com.example.climate.network.RetrofitBuilder
import com.example.climate.utils.Status
import com.example.climate.viewmodel.ViewModelFactory
import com.example.climate.viewmodel.WeatherViewModel

class DetailFragment : Fragment() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var viewDataBinding: ViewDataBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        activity?.title = "City Weather";
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(
                AppDatabase.getInstance(requireActivity()).coord(),
                RetrofitBuilder.apiService
            )
        ).get(WeatherViewModel::class.java)
        viewModel.getCityWeather(viewModel.location)

        addObserver()

    }

    private fun addObserver() {
//        viewModel.getCityWeather(viewModel.location).observe(
//            viewLifecycleOwner, {
//                if (it != null) {
//                    viewDataBinding.setVariable(BR.weather, viewModel.weather.value)
//                }
//            })
        viewModel.weather.observe(requireActivity(), Observer {
            it.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        resource.data?.let { list ->
                            viewDataBinding.setVariable(BR.weather, list)
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

    companion object {
        fun newInstance(): Fragment {
            return DetailFragment()
        }
    }
}
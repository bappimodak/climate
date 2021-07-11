package com.example.climate.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.climate.R
import com.example.climate.model.City

class CityAdapter(private var ctx: Context, private var callback: CityAdapter.CallBack) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    var list:List<City> = ArrayList()

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var fullCityLayout: ConstraintLayout = v.findViewById(R.id.fullCityLayout)
        var cityTextView: TextView = v.findViewById(R.id.cityNameTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_row_city, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cityTextView.text = list[position].cityName
        holder.fullCityLayout.setOnClickListener {
            callback.cityOnClick(city = list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    public interface CallBack{
        fun cityOnClick(city: City);

    }
}
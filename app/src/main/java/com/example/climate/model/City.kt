package com.example.climate.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "City")
data class City(
    @SerializedName("cityName")
    @ColumnInfo(name = "cityName")
    val cityName: String,

    @SerializedName("lon")
    @ColumnInfo(name = "lon")
    var lon: Double? = null,

    @SerializedName("lat")
    @ColumnInfo(name = "lat")
    var lat: Double? = null

) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

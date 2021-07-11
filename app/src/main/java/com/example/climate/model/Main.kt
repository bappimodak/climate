package com.example.climate.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//@Entity(tableName = "Main")
data class Main(
    @SerializedName("temp")
    @ColumnInfo(name = "temp")
    var temp: Double? = null,

    @SerializedName("feels_like")
    @ColumnInfo(name = "feels_like")
    var feelsLike: Double? = null,

    @SerializedName("temp_min")
    @ColumnInfo(name = "temp_min")
    var tempMin: Double? = null,

    @SerializedName("temp_max")
    @ColumnInfo(name = "temp_max")
    var tempMax: Double? = null,

    @SerializedName("pressure")
    @ColumnInfo(name = "pressure")
    var pressure: Int? = null,

    @SerializedName("humidity")
    @ColumnInfo(name = "humidity")
    var humidity: Int? = null
){

//    @PrimaryKey(autoGenerate = true)
//    var uid: Int = 1

}
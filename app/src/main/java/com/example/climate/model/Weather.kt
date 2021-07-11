package com.example.climate.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//@Entity(tableName = "Weather")
data class Weather(

    @SerializedName("coord")
    @Ignore
    var coord: City? = null,

    @SerializedName("main")
    @Ignore
    var main: Main? = null,

    @SerializedName("id")
    @Ignore
    var id: Int? = null,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String? = null
){
//    @PrimaryKey(autoGenerate = true)
//    var uid: Int = 0

}
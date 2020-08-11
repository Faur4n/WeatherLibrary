package com.example.getweather.dto


import com.google.gson.annotations.SerializedName

data class Parent(
    @SerializedName("latt_long")
    val lattLong: String,
    @SerializedName("location_type")
    val locationType: String,
    val title: String,
    val woeid: Int
)
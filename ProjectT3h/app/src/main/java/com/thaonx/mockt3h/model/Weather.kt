package com.thaonx.mockt3h.model

import com.google.gson.annotations.SerializedName

data class Weather(
    val code: String,
    val cnt: Int,
    val list: ArrayList<WeatherItem>
)

data class WeatherItem(
    val dt: Int,
    val main: Main,
    val weather: ArrayList<WeatherInfo>
)

data class WeatherInfo(
    val icon: String
)

data class Main(
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val teamMax: Double,

    )
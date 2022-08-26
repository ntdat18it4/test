package com.thaonx.mockt3h.network

import com.thaonx.mockt3h.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast")
    suspend fun getDataFomApi(
        @Query("q") query: String,
        @Query("units") units: String,
        @Query("cnt") time: Int,
        @Query("appid") appid: String,
    ): Weather
}
package com.thaonx.mockt3h.network

import com.thaonx.mockt3h.model.Covid
import retrofit2.http.GET

interface CovidApi {
    @GET("corona/api")
    suspend fun getCovid(): Covid
}
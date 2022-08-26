package com.thaonx.mockt3h.network


import com.thaonx.mockt3h.model.Item
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("q") q: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
    ): Item
}
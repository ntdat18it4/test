package com.thaonx.mockt3h.repository

import com.thaonx.mockt3h.model.ArticlesFavorite
import com.thaonx.mockt3h.model.Covid
import com.thaonx.mockt3h.model.Item
import com.thaonx.mockt3h.model.Weather
import kotlinx.coroutines.flow.Flow

interface IItemRepository {

    //network
    suspend fun getAllNews(q: String): Item

    suspend fun getAllCovid(): Covid
    suspend fun getAllItemWeather(city: String):Weather

    //database

    suspend fun insertArticleFavorite(articlesFavorite: ArticlesFavorite)

    fun getAllArticleFavorite(): Flow<List<ArticlesFavorite>>

    suspend fun deleteArticleFavorite(articlesFavorite: ArticlesFavorite)
}
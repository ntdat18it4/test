package com.thaonx.mockt3h.repository


import com.thaonx.mockt3h.db.NewsFavoriteDao
import com.thaonx.mockt3h.model.ArticlesFavorite
import com.thaonx.mockt3h.model.Covid
import com.thaonx.mockt3h.model.Item
import com.thaonx.mockt3h.model.Weather
import com.thaonx.mockt3h.network.NewsApi
import com.thaonx.mockt3h.network.CovidApi
import com.thaonx.mockt3h.network.WeatherApi
import com.thaonx.mockt3h.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val api: NewsApi,
    private val covidApi: CovidApi,
    private val weatherApi: WeatherApi,
    private val newsFavoriteDao: NewsFavoriteDao,
) : IItemRepository {

    override suspend fun getAllNews(q: String): Item {
        return withContext(Dispatchers.IO){
            api.getNews(q, Constants.SORT_BY,Constants.API_KEY)
        }
    }

    override suspend fun getAllCovid(): Covid {
        return withContext(Dispatchers.IO) {
            covidApi.getCovid()
        }
    }

    override suspend fun getAllItemWeather(city: String): Weather {
        return withContext(Dispatchers.IO) {
            weatherApi.getDataFomApi(city,
                Constants.UNITS,
                Constants.TIMES,
                Constants.API_KEY_WEATHER)
        }
    }

    //database

    override suspend fun insertArticleFavorite(articlesFavorite: ArticlesFavorite) {
        return withContext(Dispatchers.IO) {
            newsFavoriteDao.insertArticleFavorite(articlesFavorite)
        }
    }

    override fun getAllArticleFavorite(): Flow<List<ArticlesFavorite>> {
        return newsFavoriteDao.getAllArticleFavorite().flowOn(Dispatchers.IO)
    }

    override suspend fun deleteArticleFavorite(articlesFavorite: ArticlesFavorite) {
        return withContext(Dispatchers.IO) {
            newsFavoriteDao.deleteArticleFavorite(articlesFavorite)
        }
    }
}
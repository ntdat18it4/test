package com.thaonx.mockt3h.di.module


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.thaonx.mockt3h.db.NewsFavoriteDao
import com.thaonx.mockt3h.network.NewsApi
import com.thaonx.mockt3h.network.CovidApi
import com.thaonx.mockt3h.network.WeatherApi
import com.thaonx.mockt3h.repository.IItemRepository
import com.thaonx.mockt3h.repository.ItemRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideItemRepository(
        api: NewsApi,
        covidApi: CovidApi,
        weatherApi: WeatherApi,
        newsFavoriteDao: NewsFavoriteDao,
    ): IItemRepository {
        return ItemRepository(api, covidApi, weatherApi, newsFavoriteDao)
    }
}
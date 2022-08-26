package com.thaonx.mockt3h.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.thaonx.mockt3h.db.NewsFavoriteDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providerDatabase(application: Application): NewsFavoriteDatabase =
        NewsFavoriteDatabase.getInstance(application)

    @Singleton
    @Provides
    fun providerNewsFavoriteDao(database: NewsFavoriteDatabase) =
        database.getNewsFavoriteDao()
}

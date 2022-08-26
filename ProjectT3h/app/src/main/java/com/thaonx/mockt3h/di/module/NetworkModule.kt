package com.thaonx.mockt3h.di.module


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.thaonx.mockt3h.network.NewsApi
import com.thaonx.mockt3h.network.CovidApi
import com.thaonx.mockt3h.network.WeatherApi
import com.thaonx.mockt3h.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOKHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideGson(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    @Named("news")
    fun provideRetrofit(
        converterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApi(@Named("news") retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    @Named("covid")
    fun provideRetrofitCovid(
        converterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_COVID)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiCovid(@Named("covid") retrofitCovid: Retrofit): CovidApi {
        return retrofitCovid.create(CovidApi::class.java)
    }

    @Provides
    @Singleton
    @Named("weather")
    fun provideRetrofitWeather(
        converterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_WEATHER)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiWeather(@Named("weather") retrofitWeather: Retrofit): WeatherApi {
        return retrofitWeather.create(WeatherApi::class.java)
    }
}
package com.islamzada.newsapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.islamzada.newsapp.data.server.ApiService
import com.islamzada.newsapp.utils.BASE_URL
import com.islamzada.newsapp.utils.TIMEOUT_TIME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Base URL'yi sağlayan fonksiyon
    @Provides
    @Singleton
    fun provideUrl() = BASE_URL

    // Gson nesnesini sağlayan fonksiyon
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    // Timeout süresini sağlayan fonksiyon
    @Provides
    @Singleton
    fun provideTimeOut() = TIMEOUT_TIME

    // OkHttpClient nesnesini sağlayan fonksiyon
    @Provides
    @Singleton
    fun provideClient(time: Long) = OkHttpClient.Builder()
        .connectTimeout(time, TimeUnit.SECONDS)
        .readTimeout(time, TimeUnit.SECONDS)
        .writeTimeout(time, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    // Retrofit nesnesini sağlayan fonksiyon
    @Provides
    @Singleton
    fun provideRetrofit(base_url: String, gson: Gson, client: OkHttpClient): ApiService =
        Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ApiService::class.java)
}





package com.islamzada.newsapp.data.server

import com.islamzada.newsapp.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService
{
    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @Query("apiKey") apiKey : String,
        @Query("country") country : String
    ) : Response<NewsResponse>
}
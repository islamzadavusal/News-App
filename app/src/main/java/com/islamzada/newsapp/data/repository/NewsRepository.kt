package com.islamzada.newsapp.data.repository

import com.islamzada.newsapp.data.model.NewsResponse
import com.islamzada.newsapp.data.server.ApiService
import com.islamzada.newsapp.utils.MyResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api : ApiService)
{
    suspend fun lastNews() : Flow<MyResponse<NewsResponse>> = flow {
        emit(MyResponse.loading())
        val response = api.getTopHeadLines(TOKEN,"us")
        if (response.isSuccessful)
            emit(MyResponse.success(response.body()))
        else emit(MyResponse.error("please try again later!"))
    }.catch {
        emit(MyResponse.error(it.message.toString()))
    }
}


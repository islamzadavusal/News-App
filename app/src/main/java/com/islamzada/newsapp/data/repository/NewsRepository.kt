package com.islamzada.newsapp.data.repository

import com.islamzada.newsapp.data.model.NewsResponse
import com.islamzada.newsapp.data.server.ApiService
import com.islamzada.newsapp.utils.MyResponse
import com.islamzada.newsapp.utils.TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface NewsRepositoryInterface {


}
class NewsRepository @Inject constructor(private val api: ApiService) : NewsRepositoryInterface {

    // En son haberleri getiren fonksiyon
    suspend fun lastNews(): Flow<MyResponse<NewsResponse>> = flow {
        // Yükleniyor durumunu yayınla
        emit(MyResponse.loading())

        // API'den en son başlıkları alma işlemi
        val response = api.getTopHeadLines(TOKEN, "us")

        // Başarılı bir cevap alındıysa başarı durumunu yayınla, aksi takdirde hata durumunu yayınla
        if (response.isSuccessful)
            emit(MyResponse.success(response.body()))
        else
            emit(MyResponse.error("Please try again later!"))
    }.catch {
        // Hata durumunu yayınla
        emit(MyResponse.error(it.message.toString()))
    }
}

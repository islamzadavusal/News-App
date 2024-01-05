package com.islamzada.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.islamzada.newsapp.data.model.Favorite
import com.islamzada.newsapp.data.server.AppDao
import javax.inject.Inject

interface FavoriteRepositoryInterface {


}

class FavoriteRepository @Inject constructor(private val appDao : AppDao) : FavoriteRepositoryInterface{

    suspend fun insertToFav (fav: Favorite) {
        appDao.insert(fav)
    }


    fun getAllFav() : LiveData<List<Favorite>> {
        return appDao.getAllFav()
    }


    suspend fun deleteFav (fav: Favorite) {
        appDao.delete(fav)
    }

}
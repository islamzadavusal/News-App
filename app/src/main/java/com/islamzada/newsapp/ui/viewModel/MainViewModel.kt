package com.islamzada.newsapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamzada.newsapp.data.model.Favorite
import com.islamzada.newsapp.data.model.NewsResponse
import com.islamzada.newsapp.data.repository.FavoriteRepository
import com.islamzada.newsapp.data.repository.NewsRepository
import com.islamzada.newsapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val newsRepository : NewsRepository, private val favRepository : FavoriteRepository) : ViewModel()
{
    var newsData = MutableLiveData<MyResponse<NewsResponse>>()
    fun getAllNotes() = viewModelScope.launch {
        newsRepository.lastNews().collect{
            newsData.postValue(it)
        }
    }

    fun insertToFav(fav: Favorite) {
        viewModelScope.launch {
            favRepository.insertToFav(fav)
        }
    }


}

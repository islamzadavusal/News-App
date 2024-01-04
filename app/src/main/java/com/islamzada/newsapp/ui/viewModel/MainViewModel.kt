package com.islamzada.newsapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamzada.newsapp.data.model.NewsResponse
import com.islamzada.newsapp.data.repository.NewsRepository
import com.islamzada.newsapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository : NewsRepository) : ViewModel()
{
    var newsData = MutableLiveData<MyResponse<NewsResponse>>()
    fun getAllNotes() = viewModelScope.launch {
        repository.lastNews().collect{
            newsData.postValue(it)
        }
    }
}

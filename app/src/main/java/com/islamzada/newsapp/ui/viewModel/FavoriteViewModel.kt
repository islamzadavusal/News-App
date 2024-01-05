package com.islamzada.newsapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamzada.newsapp.data.model.Favorite
import com.islamzada.newsapp.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor (var favRepository: FavoriteRepository): ViewModel() {


    fun getAllDataFav(): LiveData<List<Favorite>> {
        return favRepository.getAllFav()
    }

    fun deleteFav(fav: Favorite) {
        viewModelScope.launch {
            favRepository.deleteFav(fav)

        }
    }

}
package com.islamzada.newsapp.di

import com.islamzada.newsapp.data.repository.FavoriteRepository
import com.islamzada.newsapp.data.repository.FavoriteRepositoryInterface
import com.islamzada.newsapp.data.repository.NewsRepository
import com.islamzada.newsapp.data.repository.NewsRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModel {

    @Binds
    abstract fun bindFavoriteRepository(frp : FavoriteRepository) : FavoriteRepositoryInterface

    @Binds
    abstract fun bindNewsRepository(frp : NewsRepository) : NewsRepositoryInterface

}
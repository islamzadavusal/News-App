package com.islamzada.newsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.islamzada.newsapp.data.model.Favorite
import com.islamzada.newsapp.data.server.AppDao

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}
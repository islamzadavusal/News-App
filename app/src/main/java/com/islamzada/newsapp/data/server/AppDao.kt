package com.islamzada.newsapp.data.server

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.islamzada.newsapp.data.model.Favorite

@Dao
interface AppDao {

    @Insert
    suspend fun insert (favorite: Favorite)

    @Query("SELECT * FROM favorite")
    fun getAllFav () : LiveData<List<Favorite>>

    @Delete
    suspend fun delete (favorite: Favorite)


}
package com.bahasyim.mygithubapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bahasyim.mygithubapp.data.local.entity.FavEntity

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavEntity)

    @Delete
    fun delete(user: FavEntity)

    @Query("SELECT * FROM favoriteTable")
    fun getAllFavoriteData(): LiveData<List<FavEntity>>

    @Query("SELECT * FROM favoriteTable WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<List<FavEntity>>
}
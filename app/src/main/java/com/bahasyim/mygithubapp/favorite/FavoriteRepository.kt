package com.bahasyim.mygithubapp.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import com.bahasyim.mygithubapp.data.local.entity.FavEntity
import com.bahasyim.mygithubapp.data.local.room.FavDao
import com.bahasyim.mygithubapp.data.local.room.FavDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val favDao: FavDao
    private val appExecutors: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavDatabase.getDatabase(application)
        favDao = db.favDao()
    }

    fun getAllFavorite(): LiveData<List<FavEntity>> = favDao.getAllFavoriteData()

    fun insert(favEntity: FavEntity){
        appExecutors.execute { favDao.insert(favEntity) }
    }

    fun delete(favEntity: FavEntity){
        appExecutors.execute { favDao.delete(favEntity) }
    }

    fun getFavoriteByUsername(username: String): LiveData<List<FavEntity>> = favDao.getFavoriteUserByUsername(username)
}
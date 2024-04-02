package com.bahasyim.mygithubapp.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bahasyim.mygithubapp.data.local.entity.FavEntity

class FavoriteViewModel(application: Application): ViewModel() {
    private val mFavoriteRepostitory: FavoriteRepository = FavoriteRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavorite(): LiveData<List<FavEntity>> = mFavoriteRepostitory.getAllFavorite()
}
package com.bahasyim.mygithubapp.userdetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bahasyim.mygithubapp.data.local.entity.FavEntity
import com.bahasyim.mygithubapp.data.response.DetailUserResponse
import com.bahasyim.mygithubapp.data.response.ItemsItem
import com.bahasyim.mygithubapp.data.retrofit.ApiConfig
import com.bahasyim.mygithubapp.favorite.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert (user: FavEntity){
        mFavoriteRepository.insert(user)
    }
    fun delete (user: FavEntity){
        mFavoriteRepository.delete(user)
    }
    fun getFavoriteByUsername(username: String) = mFavoriteRepository.getFavoriteByUsername(username)

    //mendapatkan data user detail: Name, Login(Username), Follower, Following
    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val userDetail: LiveData<DetailUserResponse> = _userDetail

    //mendapatkan data followers dan following data <List>
    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    //loading data
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
       private const val TAG = "UserDetailViewModel"
        var DETAIL_ID = "123"
    }

    fun setUserDetail(userDetail: String) {
        DETAIL_ID = userDetail
        findUserDetailResponse()
    }

    private fun findUserDetailResponse() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(DETAIL_ID)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                    getFollowers()
                    getFollowing()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }


    private fun getFollowers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(DETAIL_ID)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun getFollowing() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(DETAIL_ID)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}
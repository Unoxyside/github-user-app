package com.bahasyim.mygithubapp.data.response

import com.google.gson.annotations.SerializedName

data class GithubResponse(
	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)

data class ItemsItem(

	@field:SerializedName("following_url") //following data
	val followingUrl: String? = null,

	@field:SerializedName("login") //username
	val login: String? = null,

	@field:SerializedName("followers_url") //follower data
	val followersUrl: String? = null,

	@field:SerializedName("avatar_url") //avatar user
	val avatarUrl: String? = null,

)

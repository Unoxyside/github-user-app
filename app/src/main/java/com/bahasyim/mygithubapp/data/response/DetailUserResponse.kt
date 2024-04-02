package com.bahasyim.mygithubapp.data.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

	@field:SerializedName("following_url") //following
	val followingUrl: String? = null,

	@field:SerializedName("login") //login (username)
	val login: String? = null,

	@field:SerializedName("followers_url") //followers
	val followersUrl: String? = null,

	@field:SerializedName("followers") //followers
	val followers: Int? = null,

	@field:SerializedName("avatar_url") //avatar url
	val avatarUrl: String? = null,

	@field:SerializedName("following") //following
	val following: Int? = null,

	@field:SerializedName("name") //name
	val name: String? = null,
)

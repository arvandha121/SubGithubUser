package com.dicoding.subgithubuser.response

import com.google.gson.annotations.SerializedName

data class UsersResponse(
	@SerializedName("login") val login: String,
	@SerializedName("html_url") val htmlUrl: String,
	@SerializedName("avatar_url") val avatarUrl: String,
	@SerializedName("id")val id: Int,
	@SerializedName("type") val type: String,
	@SerializedName("url") val url: String,
)
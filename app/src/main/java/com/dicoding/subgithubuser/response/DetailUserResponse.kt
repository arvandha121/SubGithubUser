package com.dicoding.subgithubuser.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
	@SerializedName("login")  val login: String,
	@SerializedName("html_url")  val htmlUrl: String,
	@SerializedName("avatar_url")  val avatarUrl: String,
	@SerializedName("score")  val score: Any,
	@SerializedName("name")  val name: String,
	@SerializedName("company")  val company: String,
	@SerializedName("location")  val location: String,
	@SerializedName("public_repos")  val public_repos: String,
	@SerializedName("followers")  val followers: Int,
	@SerializedName("following")  val following: Int,
	@SerializedName("created_at")  val createdAt: String,
)

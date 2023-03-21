package com.dicoding.subgithubuser.response.main

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersResponse(
	@SerializedName("login") val login: String,
	@SerializedName("html_url") val htmlUrl: String,
	@SerializedName("avatar_url") val avatarUrl: String,
	@SerializedName("id")val id: Int,
	@SerializedName("type") val type: String,
	@SerializedName("url") val url: String,
) : Parcelable
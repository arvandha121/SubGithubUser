package com.dicoding.subgithubuser.data.response.main

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName="favorite")
@Parcelize
data class UsersResponse(
	@ColumnInfo(name = "avatar_url")
	@SerializedName("avatar_url") val avatarUrl: String,

	@PrimaryKey
	@ColumnInfo(name = "id")
	@SerializedName("id") val id: Int,

	@ColumnInfo(name = "login")
	@SerializedName("login") val login: String,
) : Parcelable
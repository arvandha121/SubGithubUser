package com.dicoding.subgithubuser.response

import com.google.gson.annotations.SerializedName

data class ListUsersResponse(
	@field:SerializedName("items")
	val items: List<UsersResponse>,
)

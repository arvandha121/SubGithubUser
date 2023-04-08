package com.dicoding.subgithubuser.data.response.main

import com.google.gson.annotations.SerializedName

data class ListUsersResponse(
    @field:SerializedName("items")
	val items: List<UsersResponse>,
)

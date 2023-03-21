package com.dicoding.subgithubuser.api

import com.dicoding.subgithubuser.BuildConfig
import com.dicoding.subgithubuser.response.DetailUserResponse
import com.dicoding.subgithubuser.response.ListUsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{
    companion object{
        private const val TOKEN = BuildConfig.TOKEN
    }

    @GET("users/{username}")
    @Headers("Authorization: token $TOKEN")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<ListUsersResponse>

    @GET("search/users")
    @Headers("Authorization: token $TOKEN")
    fun getSearchUsers(
        @Query("q") query: String
    ) : Call<DetailUserResponse>
}
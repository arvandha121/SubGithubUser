package com.dicoding.subgithubuser.data.api

import com.dicoding.subgithubuser.BuildConfig
import com.dicoding.subgithubuser.data.response.detail.DetailResponse
import com.dicoding.subgithubuser.data.response.main.ListUsersResponse
import com.dicoding.subgithubuser.data.response.main.UsersResponse
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
    ): Call<DetailResponse>

    @GET("search/users")
    @Headers("Authorization: token $TOKEN")
    fun getSearchUsers(
        @Query("q") query: String
    ) : Call<ListUsersResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $TOKEN")
    fun getFollower(
        @Path("username") username: String
    ): Call<List<UsersResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $TOKEN")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UsersResponse>>
}
package com.dicoding.subgithubuser.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.subgithubuser.api.ApiConfig
import com.dicoding.subgithubuser.response.main.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel(){
    private val _listFollower = MutableLiveData<List<UsersResponse>>()
    val listFollower: LiveData<List<UsersResponse>> = _listFollower

    private val _listFollowing = MutableLiveData<List<UsersResponse>>()
    val listFollowing: LiveData<List<UsersResponse>> = _listFollowing

    private val _snackBarText = MutableLiveData<Event<String>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getFollower(username: String){
        if(_listFollower.value != null) return

        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollower(username)
        client.enqueue(object : Callback<List<UsersResponse>> {
            override fun onResponse(
                call: Call<List<UsersResponse>>,
                response: Response<List<UsersResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollower.value = response.body()
                } else {
                    _snackBarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {
                _snackBarText.value = Event(t.message.toString())
                _isLoading.value = false
            }

        })
    }

    fun getFollowing(username: String){
        if(_listFollowing.value != null) return

        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<UsersResponse>> {
            override fun onResponse(
                call: Call<List<UsersResponse>>,
                response: Response<List<UsersResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    _snackBarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {
                _isLoading.value = false
                _snackBarText.value = Event(t.message.toString())
            }

        })
    }
}
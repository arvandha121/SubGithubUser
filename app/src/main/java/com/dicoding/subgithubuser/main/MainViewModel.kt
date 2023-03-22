package com.dicoding.subgithubuser.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.subgithubuser.api.ApiConfig
import com.dicoding.subgithubuser.response.main.ListUsersResponse
import com.dicoding.subgithubuser.response.main.UsersResponse
import com.dicoding.subgithubuser.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listUser = MutableLiveData<List<UsersResponse>>()
    val listUser: LiveData<List<UsersResponse>> = _listUser

    private val _snackBarText = MutableLiveData<Event<String>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findUsers("dicoding")
    }

    fun findUsers(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUsers(query)
        client.enqueue(object: Callback<ListUsersResponse> {
            override fun onResponse(
                call: Call<ListUsersResponse>,
                response: Response<ListUsersResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listUser.value = response.body()?.items
                }else{
                    _snackBarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<ListUsersResponse>, t: Throwable) {
                _isLoading.value = false
                _snackBarText.value = Event(t.message.toString())
            }
        })
    }
}

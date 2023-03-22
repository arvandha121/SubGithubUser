package com.dicoding.subgithubuser.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.subgithubuser.api.ApiConfig
import com.dicoding.subgithubuser.response.detail.DetailResponse
import com.dicoding.subgithubuser.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel()  {
    private val _DetailUser = MutableLiveData<DetailResponse>()
    private val _snackBarText = MutableLiveData<Event<String>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : MutableLiveData<Boolean> = _isLoading

    val detailUser: LiveData<DetailResponse> = _DetailUser

    fun getUserDetail(username: String){
        if(_DetailUser.value != null) return

        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUsers(username)
        client.enqueue(object : Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _DetailUser.postValue(response.body())
                } else {
                    _snackBarText.value = Event(response.message())

                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                _snackBarText.value = Event(t.message.toString())
            }
        })
    }
}
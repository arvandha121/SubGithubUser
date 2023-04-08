package com.dicoding.subgithubuser.ui.detail

import androidx.lifecycle.*
import com.dicoding.subgithubuser.data.api.ApiConfig
import com.dicoding.subgithubuser.data.response.detail.DetailResponse
import com.dicoding.subgithubuser.data.response.main.UsersResponse
import com.dicoding.subgithubuser.data.room.database.DatabaseModule
import com.dicoding.subgithubuser.util.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val db: DatabaseModule) : ViewModel() {
    private val _DetailUser = MutableLiveData<DetailResponse>()
    private val _snackBarText = MutableLiveData<Event<String>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    val detailUser: LiveData<DetailResponse> = _DetailUser

    val resultSuccessFavorite = MutableLiveData<Boolean>()
    val resultDeleteFavorite = MutableLiveData<Boolean>()

    private var isFavorite = false

    fun setFavorite(user: UsersResponse?) {
        viewModelScope.launch {
            user?.let {
                if (isFavorite) {
                    db.userDao.delete(user)
                    resultDeleteFavorite.value = true
                } else {
                    db.userDao.insert(user)
                    resultSuccessFavorite.value = true
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun findFavorite(id: Int, listenFavorite: () -> Unit) {
        viewModelScope.launch {
            val user = db.userDao.findById(id)
            if (user != null) {
                listenFavorite()
            }
        }
    }

    fun getUserDetail(username: String) {
        if (_DetailUser.value != null) return

        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUsers(username)
        client.enqueue(object : Callback<DetailResponse> {
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

    class Factory(private val db: DatabaseModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailViewModel(db) as T
        }
    }
}
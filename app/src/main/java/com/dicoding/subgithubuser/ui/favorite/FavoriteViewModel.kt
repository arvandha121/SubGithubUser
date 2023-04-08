package com.dicoding.subgithubuser.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.subgithubuser.data.room.database.DatabaseModule

class FavoriteViewModel(private val db: DatabaseModule)  : ViewModel(){

    fun getFavorite() = db.userDao.loadAll()

    class Factory(private val db: DatabaseModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoriteViewModel(db) as T
        }
    }
}
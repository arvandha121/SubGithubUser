package com.dicoding.subgithubuser.data.room.database

import android.content.Context
import androidx.room.Room

class DatabaseModule(private val context: Context) {
    private val db = Room.databaseBuilder(
        context,
        FavoriteDatabase::class.java,
        "favorite.db"
    )
        .allowMainThreadQueries()
        .build()

    val userDao = db.userDao()
}
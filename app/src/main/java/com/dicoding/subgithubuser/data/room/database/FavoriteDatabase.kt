package com.dicoding.subgithubuser.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.subgithubuser.data.response.main.UsersResponse
import com.dicoding.subgithubuser.data.room.dao.UserDao

@Database(entities = [UsersResponse::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}
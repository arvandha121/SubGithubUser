package com.dicoding.subgithubuser.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.subgithubuser.data.response.main.UsersResponse

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: UsersResponse)

    @Query("SELECT * FROM favorite")
    fun loadAll(): LiveData<List<UsersResponse>>

    @Query("SELECT * FROM favorite WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): UsersResponse

    @Delete
    fun delete(user: UsersResponse)
}
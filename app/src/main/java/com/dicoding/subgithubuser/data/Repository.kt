package com.dicoding.subgithubuser.data

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import com.dicoding.subgithubuser.data.room.dao.UserDao
import com.dicoding.subgithubuser.data.room.database.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

//class Repository(application: Application) {
//    private val mDao: UserDao
//    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
//
//    init {
//        val db = FavoriteDatabase.getDatabase(application)
//        mNotesDao = db.noteDao()
//    }
//
//    fun getAllNotes(): LiveData<List<ContactsContract.CommonDataKinds.Note>> = mNotesDao.getAllNotes()
//
//    fun insert(note: ContactsContract.CommonDataKinds.Note) {
//        executorService.execute { mNotesDao.insert(note) }
//    }
//
//    fun delete(note: ContactsContract.CommonDataKinds.Note) {
//        executorService.execute { mNotesDao.delete(note) }
//    }
//
//    fun update(note: ContactsContract.CommonDataKinds.Note) {
//        executorService.execute { mNotesDao.update(note) }
//    }
//}
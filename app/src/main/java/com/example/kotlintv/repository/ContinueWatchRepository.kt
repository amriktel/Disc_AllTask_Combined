package com.example.kotlintv.repository

import androidx.lifecycle.LiveData
import com.example.kotlinroomdatabase.data.ContinuWatchDao
import com.example.kotlintv.model.ContinueWatch

// User Repository abstracts access to multiple data sources. However this is not the part of the Architecture Component libraries.

class ContinueWatchRepository(private val mContinueWatchDao: ContinuWatchDao) {
    /*val readAllData: LiveData<List<ContinueWatch>> = userDao.readAllData()

    suspend fun addUser(user: ContinueWatch) {
        userDao.addContinueWatch(user)
    }

    suspend fun updateUser(user: ContinueWatch) {
        userDao.updateContinueWatch(user)
    }

    suspend fun deleteUser(user: ContinueWatch) {
        userDao.deleteContinueWatch(user)
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAllContinueWatch()
    }
    suspend fun readAllUsers() {
        userDao.readAllData()
    }*/
}
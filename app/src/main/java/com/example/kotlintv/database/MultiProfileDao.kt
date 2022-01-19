package com.example.kotlintv.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlintv.model.MultiProfile

@Dao
interface MultiProfileDao {

    @Query("SELECT * FROM multi_profile_table")
    fun getAll(): List<MultiProfile>

    @Query("SELECT * FROM multi_profile_table WHERE user_Id= :id ")
    fun getMultiProfile(id: String): List<MultiProfile>

    @Insert
    fun insertAll(vararg multiProfile: MultiProfile)
}
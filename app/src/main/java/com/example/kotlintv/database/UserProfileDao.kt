package com.example.kotlintv.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserProfileDao {

    @Insert
    suspend fun insertUser(userTable: UserTable)

    @Query("SELECT * FROM USER_PROFILE WHERE user_id = :userId")
    fun getUserDetail(userId: String) : LiveData<UserTable>

    @Query("UPDATE USER_PROFILE SET user_name=:name, user_email=:email, user_gender=:gender WHERE user_id = :userId")
    suspend fun updateUserDetail(userId: String, name: String, email: String, gender: String)

    @Query("DELETE FROM USER_PROFILE WHERE user_id=:userId")
    suspend fun deleteUser(userId: String)

}
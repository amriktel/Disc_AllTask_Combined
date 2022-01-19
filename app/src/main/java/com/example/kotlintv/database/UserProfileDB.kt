package com.example.kotlintv.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserTable::class], version = 1)
abstract class UserProfileDB :RoomDatabase() {

    abstract fun getUserProfileDao() : UserProfileDao

    companion object {
        private var userProfileDB: UserProfileDB? = null
        fun getUserDatabase(context: Context): UserProfileDB? {
            if (userProfileDB == null){
                userProfileDB = Room.databaseBuilder(context, UserProfileDB::class.java, "user_profile_db")
                    .build()
            }
            return userProfileDB
        }
    }
}
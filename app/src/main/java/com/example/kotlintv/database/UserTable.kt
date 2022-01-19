package com.example.kotlintv.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserTable(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val user_id : String,
    val user_name : String,
    val user_email : String,
    val user_gender : String
)

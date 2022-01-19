package com.example.kotlintv.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_list")
data class MyListTable(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    var showName: String,
    var startTime: String,
    var endTime: String,
    var duration: String,
    var timeLeft: String,
    var episode: String,
    var description: String,
    var videoUrl: String,
    var backgroundcardImageUrl: String,
    var cardImageUrl: String
)

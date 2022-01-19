package com.example.kotlintv.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "continuewatchtray_table")
data class ContinueWatchTrayTable(

    @PrimaryKey(autoGenerate = true)
    val id: Int, // <- 'id' is the primary key which will be autogenerated by the Room library.
    val uniqueContentId: String,
    val lastWatchDuration: Long,
    val totalDuration: Long,
    var title: String,
    var cardImageUrl: String

)

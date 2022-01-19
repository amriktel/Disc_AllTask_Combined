package com.example.kotlintv.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "multi_profile_table")
data class MultiProfile (
    @ColumnInfo(name = "user_id") val userId: String?,
    @ColumnInfo(name = "profile_name") val profileName: String?,
    @ColumnInfo(name = "profile_type") val profileType: Int?,
    @ColumnInfo(name = "age_group") val ageGroup: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0;
}
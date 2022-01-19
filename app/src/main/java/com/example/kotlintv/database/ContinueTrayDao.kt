package com.example.kotlintv.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlintv.model.ContinueWatch
import kotlinx.coroutines.flow.Flow


@Dao
interface ContinueTrayDao {

    @Query("SELECT * FROM continuewatchtray_table ORDER BY id ASC")
    fun getCWTrayAllData(): List<ContinueWatchTrayTable>

    @Query("Select * from continuewatchtray_table")
    fun getAllData(): LiveData<List<ContinueWatchTrayTable>>

    @Query("SELECT EXISTS (SELECT 1 FROM continuewatchtray_table WHERE uniqueContentId = :uniqueContentId)")
    fun exists(uniqueContentId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tray: ContinueWatchTrayTable)

    //@Update(onConflict = OnConflictStrategy.REPLACE)
    @Query("UPDATE continuewatchtray_table SET lastWatchDuration = :lastWatchDuration WHERE uniqueContentId =:uniqueContentId")
    suspend fun update(uniqueContentId: String, lastWatchDuration: Long)

    @Query("DELETE FROM continuewatchtray_table")
    suspend fun deleteAll()
}
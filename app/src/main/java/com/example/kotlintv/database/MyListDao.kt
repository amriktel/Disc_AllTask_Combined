package com.example.kotlintv.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlintv.model.GuideInfoList

@Dao
interface MyListDao {
    @Insert
    suspend fun insertWatchList(myListTable: MyListTable)

    @Query("DELETE FROM my_list WHERE id =:contentId")
    suspend fun removeWatchList(contentId: String):Int


    @Query("SELECT EXISTS (SELECT 1 FROM my_list WHERE id = :contentId)")
    fun exists(contentId: String): Boolean

    @Query("Select * from my_list")
    fun getAllData(): List<GuideInfoList>


}
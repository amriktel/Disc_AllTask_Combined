package com.example.kotlinroomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kotlintv.model.ContinueWatch
import kotlinx.coroutines.flow.Flow

// UserDao contains the methods used for accessing the database, including queries.
@Dao
interface ContinuWatchDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM continuewatch_table ORDER BY id ASC")
    fun getContinueWatchAllData(): Flow<List<ContinueWatch>>

    @Query("Select * from continuewatch_table")
    fun getAllData(): List<ContinueWatch>

    @Query("SELECT EXISTS (SELECT 1 FROM continuewatch_table WHERE contentId = :contentId)")
    fun exists(contentId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: ContinueWatch)

    //@Update(onConflict = OnConflictStrategy.REPLACE)
    @Query("UPDATE continuewatch_table SET lastWatchDuration = :lastWatchDuration WHERE contentId =:contentId")
    suspend fun update(contentId: Int, lastWatchDuration: Long)

    @Query("DELETE FROM continuewatch_table")
    suspend fun deleteAll()

    /* @Insert(onConflict = OnConflictStrategy.REPLACE) // <- Annotate the 'addUser' function below. Set the onConflict strategy to IGNORE so if exactly the same user exists, it will just ignore it.
     suspend fun addContinueWatch(user: ContinueWatch)

     @Update
     suspend fun updateContinueWatch(user: ContinueWatch)

     @Delete
     suspend fun deleteContinueWatch(user: ContinueWatch)

     @Query("DELETE FROM continuewatch_table")
     suspend fun deleteAllContinueWatch()

     @Query("SELECT * from continuewatch_table ORDER BY id ASC") // <- Add a query to fetch all users (in user_table) in ascending order by their IDs.
     fun readAllData(): LiveData<List<ContinueWatch>> // <- This means function return type is List. Specifically, a List of Users.*/
}
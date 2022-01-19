package com.example.kotlintv.viewModel

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.kotlinroomdatabase.data.ContinuWatchDao
import com.example.kotlintv.model.ContinueWatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WatchRepository(private val continueWatchDao: ContinuWatchDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val mContinueWatch: Flow<List<ContinueWatch>> = continueWatchDao.getContinueWatchAllData()
//    val mContinueWatchAllData: List<ContinueWatch> = continueWatchDao.getAllData()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: ContinueWatch) {
            if (continueWatchDao.exists(word.contentId))
                continueWatchDao.update(word.contentId,word.lastWatchDuration)
            else
                continueWatchDao.insert(word)
        Log.d("------>","insert inside repository")
    }
    suspend fun getAllData() :List<ContinueWatch>{
        return continueWatchDao.getAllData()
    }
}
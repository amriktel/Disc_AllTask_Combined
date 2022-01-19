package com.example.kotlintv.viewModel

import android.app.Application
import com.example.kotlinroomdatabase.data.ContinueWatchDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WatchApplication: Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { ContinueWatchDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WatchRepository(database.continueWatchDao()) }
}
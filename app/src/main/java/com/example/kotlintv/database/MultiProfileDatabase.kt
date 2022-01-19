package com.example.kotlintv.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlinroomdatabase.data.ContinueWatchDatabase
import com.example.kotlintv.model.MultiProfile
import kotlinx.coroutines.CoroutineScope

@Database(entities = [MultiProfile::class], version = 1)
abstract class MultiProfileDatabase  : RoomDatabase() {
    abstract fun MultiProfileDao(): MultiProfileDao

    companion object {
        @Volatile
        private var INSTANCE: MultiProfileDatabase? = null

        fun getDatabase(
            context: Context,
        ): MultiProfileDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MultiProfileDatabase::class.java,
                    "multi_profile_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
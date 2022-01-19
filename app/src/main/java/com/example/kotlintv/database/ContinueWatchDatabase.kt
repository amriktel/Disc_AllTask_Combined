package com.example.kotlinroomdatabase.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kotlintv.model.ContinueWatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// UserDatabase represents database and contains the database holder and server the main access point for the underlying connection to your app's persisted, relational data.

@Database(entities = [ContinueWatch::class], version = 1)
abstract class ContinueWatchDatabase: RoomDatabase() { // <- Add 'abstract' keyword and extends RoomDatabase
    abstract fun continueWatchDao(): ContinuWatchDao

    /* companion object {
         @Volatile
         private var INSTANCE: ContinueWatchDatabase? = null

         fun getDatabase(context: Context): ContinueWatchDatabase{
             val tempInstance = INSTANCE

             if (tempInstance != null) {
                 return tempInstance
             }
             synchronized(this) {
                 val instance = Room.databaseBuilder(
                     context.applicationContext,
                     ContinueWatchDatabase::class.java,
                     "user_database"
                 ).build()
                 INSTANCE = instance
                 return instance
             }
         }
     }*/


    companion object {
        @Volatile
        private var INSTANCE: ContinueWatchDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ContinueWatchDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContinueWatchDatabase::class.java,
                    "continue_watch_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.continueWatchDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(watchDao: ContinuWatchDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            /*watchDao.deleteAll()

            var watch = ContinueWatch(0,55,22,4)
            var watch1 = ContinueWatch(1,66,33,5)
            var watch2 = ContinueWatch(2,67,44,6)
            var watch3 = ContinueWatch(3,68,55,7)
            watchDao.insert(watch)
            watchDao.insert(watch1)
            watchDao.insert(watch2)
            watchDao.insert(watch3)*/

        }
    }


}
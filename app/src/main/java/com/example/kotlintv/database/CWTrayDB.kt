package com.example.kotlintv.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlinroomdatabase.data.ContinuWatchDao


@Database(entities = [ContinueWatchTrayTable::class], version = 1)
abstract class CWTrayDB : RoomDatabase() {

    abstract fun getContinueTrayDao() : ContinueTrayDao

    companion object {
        private var cwTrayDB: CWTrayDB? = null
        fun getCWTrayDatabase(context: Context): CWTrayDB? {
            if (cwTrayDB == null){
                cwTrayDB = Room.databaseBuilder(context, CWTrayDB::class.java, "continuewatchtray_table_db")
                    .build()
            }
            return cwTrayDB
        }
    }
}
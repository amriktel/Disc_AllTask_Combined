package com.example.kotlintv.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyListTable::class], version = 1)
abstract class MyListDB :RoomDatabase() {

    abstract fun getMyListDao() : MyListDao

    companion object {
        private var myListDB: MyListDB? = null
        fun getMyListDatabase(context: Context): MyListDB? {
            if (myListDB == null){
                myListDB = Room.databaseBuilder(context, MyListDB::class.java, "my_list_db")
                    .build()
            }
            return myListDB
        }
    }
}
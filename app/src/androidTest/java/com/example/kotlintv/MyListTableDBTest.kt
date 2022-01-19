package com.example.kotlintv

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.kotlintv.database.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MyListTableDBTest {

    private lateinit var mMyListDao: MyListDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mMyListDao = MyListDB.getMyListDatabase(context)?.getMyListDao()!!
    }


    @Test
    fun testAddWatchList() = runBlocking {
        mMyListDao.insertWatchList(
            MyListTable(
                "5",
                "Test",
                "1:0",
                "2:0",
                "",
                "",
                "",
                "Test Description",
                "",
                "",
                ""
            )
        )
        val actual = mMyListDao.getAllData()
        assertNotEquals(mMyListDao, actual)
    }

    @Test
    fun testRemoveWatchList() = runBlocking {
        mMyListDao.removeWatchList("5")
        val actual = mMyListDao.getAllData()
        assertNotEquals(mMyListDao, actual)
    }

    @Test
    fun testDataExist() = runBlocking {
        mMyListDao.exists("5")
        val actual = mMyListDao.getAllData()
        assertNotEquals(mMyListDao, actual)
    }
}
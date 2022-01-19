package com.example.kotlintv

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.kotlintv.database.CWTrayDB
import com.example.kotlintv.database.ContinueTrayDao
import com.example.kotlintv.database.ContinueWatchTrayTable
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CWTableDBTest {

    private lateinit var mContinueTrayDao: ContinueTrayDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mContinueTrayDao = CWTrayDB.getCWTrayDatabase(context)?.getContinueTrayDao()!!
    }


    @Test
    fun writeAndReadLanguage() = runBlocking {
        mContinueTrayDao.insert(
            ContinueWatchTrayTable(
                0,
                "",
                0,
                0,
                "",
                ""
            )
        )
        val actual = mContinueTrayDao.getCWTrayAllData()
        assertNotEquals(mContinueTrayDao, actual)
    }

    @Test
    fun testUpdate() = runBlocking {
        mContinueTrayDao.update("",0)
        val actual = mContinueTrayDao.getCWTrayAllData()
        assertNotEquals(mContinueTrayDao, actual)
    }
}
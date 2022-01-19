package com.example.kotlintv.activity

import android.text.format.DateFormat.getTimeFormat
import androidx.test.platform.app.InstrumentationRegistry
import com.example.kotlintv.fragment.SearchFragment
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.util.concurrent.TimeUnit

class SignInActivityTest {
    @Mock
    private lateinit var signInActivity: SignInActivity
    @Before
    fun setUp() {
        signInActivity = Mockito.mock(SignInActivity::class.java)
    }

    @Test
    fun getRandomNumber() {
        var ret:Int = signInActivity.getRandomNumber(10,5)
        assertNotNull(ret)
    }
    @Test
    fun getTimeFormat() {
        val millis:Long = 100000
        var str = String.format(
            "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(millis)
            ), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millis)
            )
        )
        Assert.assertNotNull(str)
    }

}



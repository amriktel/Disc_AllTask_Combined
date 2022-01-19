package com.example.kotlintv

import android.media.MediaPlayer
import android.view.View
import android.widget.ImageView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.kotlintv.activity.SplashActivity
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashScreenTest: TestCase() {
    @get:Rule
    val activityRule : ActivityTestRule<SplashActivity> =  ActivityTestRule(SplashActivity::class.java)
    private lateinit var gifImageView : ImageView
    private lateinit var mediaPlayer: MediaPlayer

    @Test
    fun splashGifDisplayed_test(){
        Espresso.onView(withId(R.id.splash_image)).check(matches(isDisplayed()))
        activityRule.runOnUiThread {
            gifImageView = activityRule.activity.loadSplashGif(R.raw.splash_gif)
        }
        Espresso.onView(isRoot()).perform(setDelay(1000))
        Assert.assertEquals(true, gifImageView.drawable != null)
    }

    @Test
    fun splashMusicPlayed_test(){
        activityRule.runOnUiThread {
            mediaPlayer = activityRule.activity.initSplashMusicHandler()
        }
        Espresso.onView(isRoot()).perform(setDelay(1000))
        Assert.assertEquals(true, mediaPlayer != null)
        Assert.assertEquals(true, mediaPlayer.isPlaying != null)
        Assert.assertEquals(true, mediaPlayer.currentPosition > 0)
    }

    @Test
    fun nextActivityLaunched_test(){
        Espresso.onView(isRoot()).perform(setDelay(5000))
        Espresso.onView(withId(R.id.main_activity_parent_layout)).check(matches(isDisplayed()))
    }

    private fun setDelay(delay: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(View::class.java)
            }

            override fun getDescription(): String {
                return "set delay"
            }
            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadForAtLeast(delay.toLong())
            }
        }
    }
}
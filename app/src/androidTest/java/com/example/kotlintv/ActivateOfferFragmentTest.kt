package com.example.kotlintv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.kotlintv.activity.SplashActivity
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ActivateOfferFragmentTest : TestCase() {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun offerActivityTest() {

        Espresso.onView(ViewMatchers.isRoot()).perform(setDelay(10000))
        onView(withId(R.id.menu_adapter))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(7, click()));
        Espresso.onView(ViewMatchers.isRoot()).perform(setDelay(2000))
        Espresso.onView(ViewMatchers.withId(R.id.offer_code_edit_txt))
            .perform(ViewActions.typeText("123456"))
        Espresso.onView(ViewMatchers.isRoot()).perform(setDelay(1000))
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.isRoot()).perform(setDelay(2000))
        Espresso.onView(ViewMatchers.withId(R.id.activate_offer_bn)).perform(ViewActions.click())
    }

    private fun setDelay(delay: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(View::class.java)
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

package com.example.kotlintv

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.kotlintv.activity.MainActivity
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class AccountDetailsActivityTest : TestCase() {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup(){
        Utils().setSharedPreference()
        mActivityTestRule.launchActivity(Intent())
    }

    @Test
    fun accountMenuItemClickTest() {
        onView(ViewMatchers.isRoot()).perform(setDelay(10000))
        onView(withId(R.id.menu_adapter))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(ViewMatchers.isRoot()).perform(setDelay(1000))

        if (DemoConstant.SIGN_IN_STATUS == "true") {

            onView(withId(R.id.account_setting_list))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,
                        click()
                    )
                )
            onView(ViewMatchers.isRoot()).perform(setDelay(1000))
            onView(withId(R.id.account_setting_list))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        1,
                        click()
                    )
                )
            onView(ViewMatchers.isRoot()).perform(setDelay(1000))
            onView(withId(R.id.account_setting_list))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        2,
                        click()
                    )
                )
            onView(ViewMatchers.isRoot()).perform(setDelay(1000))
        }
    }

    private fun setDelay(delay: Int): ViewAction {
        return object : ViewAction {

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(View::class.java)
            }

            override fun getDescription(): String {
                return "set delay"
            }

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(delay.toLong())
            }
        }
    }
}
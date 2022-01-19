package com.example.kotlintv.activity

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintv.R
import com.example.kotlintv.fragment.SearchFragment


class SearchActivity : AppCompatActivity() {

     var mFragment: SearchFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mFragment = supportFragmentManager
            .findFragmentById(R.id.search_fragment) as SearchFragment?
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // If there are no results found, press the left key to reselect the microphone

        // If there are no results found, press the left key to reselect the microphone
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP ) {
             mFragment!!.focusOnSearch();
        }
        return super.onKeyDown(keyCode, event)
    }
}
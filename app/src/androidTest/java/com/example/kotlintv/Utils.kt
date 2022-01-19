package com.example.kotlintv

import androidx.preference.PreferenceManager
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

class Utils {
    fun setSharedPreference() {
        val targetContext = getInstrumentation().targetContext
        val preferencesEditor = PreferenceManager.getDefaultSharedPreferences(targetContext).edit()
        preferencesEditor.clear()
        preferencesEditor.putBoolean(DemoConstant.SIGN_IN_STATUS, true)
        preferencesEditor.commit()
    }
}

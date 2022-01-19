package com.example.kotlintv.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import com.example.kotlintv.DemoConstant
import com.example.kotlintv.R
import java.util.concurrent.TimeUnit

class SignInActivity : FragmentActivity() {
    private lateinit var mCodeTv: TextView
    private lateinit var mTimerTv: TextView
    private lateinit var mCountDownTimer: CountDownTimer
    private val mCodeList: ArrayList<String> = arrayListOf(
        "abcd",
        "abce",
        "ebca",
        "acbe",
        "ecad",
        "dbca",
        "dacb",
        "dbac",
        "acbd",
        "ecbd",
        "xyza",
        "edfc",
        "xyef",
        "mcvd",
    )

    private val validateList: ArrayList<String> =
        arrayListOf("abcd", "abce", "ebca", "acbe", "ecad", "dbca", "dacb", "dbac", "acbd", "ecbd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        mCodeTv = findViewById(R.id.textDisplayCode)
        mTimerTv = findViewById(R.id.timer)
        mCodeTv.text = mCodeList[getRandomNumber(0, mCodeList.size - 1)]
        callTimer()
    }

    fun getRandomNumber(min: Int, max: Int): Int {
        return (Math.random() * (max - min) + min).toInt()
    }

    fun callTimer() {
        mCountDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("CountDownTimer_SignIn", "onTick: $millisUntilFinished")
                mTimerTv.text = getTimeFormat(millisUntilFinished)
                if (millisUntilFinished < 5000L) {
                    val code = mCodeTv.text
                    if (validateList.contains(code)) {
                        Log.d("CountDownTimer_SignIn", "onTick: $code")
                        setSignInStatus(code.toString())
                        Toast.makeText(
                            applicationContext,
                            "Signed in successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        cancel()
                        finish()
                    }
                }
            }

            override fun onFinish() {

                Log.d("CountDownTimer_SignIn", "onFinish:")
                mTimerTv.text = getString(R.string.txt_zero)
                cancel()
                mCodeTv.text = mCodeList[getRandomNumber(0, mCodeList.size - 1)]
                callTimer()
            }
        }.start()
    }

    private fun setSignInStatus(code: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val editor = sharedPreferences.edit()
        editor.putBoolean(DemoConstant.SIGN_IN_STATUS, true)
        editor.putString(DemoConstant.SIGNED_IN_USER_ID, code) // ecad
        editor.apply()
    }

    fun getTimeFormat(millis: Long): String {
        return String.format(
            "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(millis)
            ), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millis)
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimer.cancel()
    }
}
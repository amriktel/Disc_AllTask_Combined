package com.example.kotlintv.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.example.kotlintv.DemoConstant
import com.example.kotlintv.R
import com.example.kotlintv.database.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class DetailsActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private lateinit var mBackgroundImage: ImageView
    private lateinit var mTitleTv: TextView
    private lateinit var mDescriptionTv: TextView
    private lateinit var mSeason: TextView
    private lateinit var mDuration: TextView
    private lateinit var mBtnWatchList: Button
    private lateinit var mBtnTts: ToggleButton
    private lateinit var id: String
    private lateinit var showName: String
    private lateinit var imageUrl: String
    private lateinit var myListDao: MyListDao
    private val addWatchList: String = "Add to MyList"
    private val removeWatchList: String = "Remove from MyList"
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @DelicateCoroutinesApi
    @SuppressLint("SetTextI18n", "WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        mTitleTv = findViewById(R.id.title)
        mDescriptionTv = findViewById(R.id.description)
        mBackgroundImage = findViewById(R.id.background_image)
        mSeason = findViewById(R.id.tv_season)
        mDuration = findViewById(R.id.tv_duration)
        mBtnWatchList = findViewById(R.id.watchlist)
        mBtnTts = findViewById(R.id.tts_on_of)
        mBtnWatchList.requestFocus()
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        editor = sharedPreferences.edit()
        myListDao = MyListDB.getMyListDatabase(this)?.getMyListDao()!!
        val isTtsEnabled = sharedPreferences.getBoolean(DemoConstant.TTS_ON_OF, false)
        mBtnTts.isChecked = isTtsEnabled
        mBtnTts.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if(tts == null){
                    tts = TextToSpeech(this, this)
                }
                editor.putBoolean(DemoConstant.TTS_ON_OF, true)
                editor.apply()
                speakOut()
            } else {
                if (tts != null) {
                    tts!!.stop()
                    tts!!.shutdown()
                }
                editor.putBoolean(DemoConstant.TTS_ON_OF, false)
                editor.apply()
            }
        }

        val extras = intent.extras
        if (extras != null) {
            val duration: String? = extras.getString("duration")
            id = extras.getString("id").toString()
            showName = extras.getString("showName").toString()
            imageUrl = extras.getString("imageUrl").toString()
            mTitleTv.text = showName
            mDescriptionTv.text = extras.getString("description")
            mDuration.text = "Duration : $duration"
            mSeason.text = extras.getString("episode")
            Glide.with(this)
                .load(imageUrl)
                .into(mBackgroundImage)
        }
        tts = TextToSpeech(this, this)
        isWatchListed(mBtnWatchList)
        mBtnWatchList.setOnClickListener {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            val isSignedIn = sharedPreferences.getBoolean(DemoConstant.SIGN_IN_STATUS, false)
            if (!isSignedIn) {
                Toast.makeText(this, "You need to login first", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, SignInActivity::class.java))
            } else {
                if (mBtnWatchList.text == addWatchList)
                    addWatchList()
                if (mBtnWatchList.text == removeWatchList)
                    removeWatchList()
            }
        }
        // getAllData()
    }


    @DelicateCoroutinesApi
    private fun addWatchList() {
        GlobalScope.launch {
            myListDao.insertWatchList(
                MyListTable(
                    id, showName,
                    "", "", "", "",
                    "", "", "", "", imageUrl
                )
            )
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(
                    this@DetailsActivity,
                    "Added to  WatchList !!!", Toast.LENGTH_LONG
                ).show()
                mBtnWatchList.text = removeWatchList
            }, 2000)
        }
    }

    @DelicateCoroutinesApi
    private fun removeWatchList() {
        GlobalScope.launch {
            myListDao.removeWatchList(id)
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(
                    this@DetailsActivity,
                    "Removed from WatchList !!!", Toast.LENGTH_LONG
                ).show()
                mBtnWatchList.text = addWatchList
            }, 2000)
        }

    }

    @DelicateCoroutinesApi
    private fun isWatchListed(mBtnWatchList: Button) {
        GlobalScope.launch {
            if (myListDao.exists(id)) {
                mBtnWatchList.text = removeWatchList
            } else {
                mBtnWatchList.text = addWatchList
            }
        }
    }


    override fun onInit(status: Int) {
        val isTtsEnabled = sharedPreferences.getBoolean(DemoConstant.TTS_ON_OF, false)
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Do nothing
            } else {
                if (isTtsEnabled)
                    speakOut()
            }
        } else {
            Log.e("Testing", "fail")
        }

    }

    private fun speakOut() {
        val text = mDescriptionTv!!.text.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}
package com.example.kotlintv.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Printer
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.kotlintv.R
import java.lang.Exception

class SplashActivity : FragmentActivity() {
    private val SPLASH_DELAY: Long = 5000
    private lateinit var splashImageView: ImageView
    private lateinit var mContext: Context
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mContext = this

        mHandler = Handler(Looper.getMainLooper())
        splashImageView = findViewById(R.id.splash_image)
        loadSplashGif(R.raw.splash_gif)
    }


    fun loadSplashGif(gifId: Int) : ImageView {
        mHandler.post {
            initSplashMusicHandler()
        }
        val gifAnimationListener = GifLoadListeners()
        Glide.with(this).asGif()
            .load(gifId)
            .listener(gifAnimationListener)
            .into(splashImageView)
        return splashImageView
    }

    fun initSplashMusicHandler() : MediaPlayer{
        mediaPlayer = MediaPlayer.create(mContext, R.raw.splash_music)
        try {
            mediaPlayer.setVolume(0.25f, 0.25f)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                mediaPlayer.stop()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mediaPlayer
    }

    private inner class GifLoadListeners : RequestListener<GifDrawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?,
            target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean = false

        override fun onResourceReady(
            resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?,
            dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            resource?.setLoopCount(1)
            resource?.registerAnimationCallback(AnimationController())
            return false
        }
    }

    private inner class AnimationController : Animatable2Compat.AnimationCallback() {
        override fun onAnimationStart(drawable: Drawable?) {
            super.onAnimationStart(drawable)
        }
        override fun onAnimationEnd(drawable: Drawable?) {
            super.onAnimationEnd(drawable)
            mHandler.postDelayed({mediaPlayer?.stop()}, 1000)
            mHandler.postDelayed({
                startActivity(Intent(mContext, MainActivity::class.java))
                finish()
            }, SPLASH_DELAY)
        }
    }

    override fun onStop() {
        super.onStop()
        mHandler?.dump({  },"Un-Init")
        mediaPlayer?.stop()
    }

}
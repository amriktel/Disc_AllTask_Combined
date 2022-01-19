package com.example.kotlintv.player.player

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintv.player.DownloadApplication
import com.example.kotlintv.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.DefaultTimeBar
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util


class VideoPlayerActivity : AppCompatActivity(), Player.Listener {

    private lateinit var selectTracksButton: ImageView
    private lateinit var httpDataSourceFactory: HttpDataSource.Factory
    private lateinit var defaultDataSourceFactory: DefaultDataSourceFactory
    private lateinit var cacheDataSourceFactory: DataSource.Factory
    private var mPlayer: SimpleExoPlayer? = null
    private lateinit var playerView: PlayerView
    private val simpleCache: SimpleCache = DownloadApplication.simpleCache
    private lateinit var mProgressBar : ProgressBar
    private lateinit var playImage : ImageView
    private lateinit var pauseImage : ImageView
    private lateinit var rewindImage : ImageView
    private lateinit var forwardImage : ImageView
    private lateinit var seekBar : DefaultTimeBar
    private lateinit var focusedView : View
    private var isShowingTrackSelectionDialog = false
    private var trackSelector: DefaultTrackSelector? = null

    private val videoURL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        //get PlayerView by its id
        playerView = findViewById(R.id.playerView)
        mProgressBar = findViewById(R.id.progress_circular)
        playImage = findViewById(R.id.exo_play)
        pauseImage = findViewById(R.id.exo_pause)
        forwardImage = findViewById(R.id.exo_ffwd)
        rewindImage = findViewById(R.id.exo_rew)
        seekBar = findViewById(R.id.exo_progress)
        selectTracksButton = findViewById(R.id.track_selector)
        focusedView = pauseImage
        trackSelector = DefaultTrackSelector(this)
        //trackSelector!!.setParameters( trackSelector!!.buildUponParameters().setSelectionOverride())
        //trackSelector!!.setParameters( trackSelector!!.buildUponParameters() .setMaxVideoSizeSd() .setPreferredAudioLanguage("deu"))
        playerView.setControllerVisibilityListener(object :PlayerControlView.VisibilityListener{
            override fun onVisibilityChange(visibility: Int) {
                if (visibility == View.VISIBLE || visibility == View.GONE) {
                    focusedView.requestFocus()
                }
            }
        })
        setListeners();
    }

    private fun setListeners() {
        pauseImage.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                pauseImage.background = application.getDrawable(R.drawable.focus_border)
                focusedView = pauseImage
            } else {
                pauseImage.background = application.getDrawable(R.drawable.unfocus_border)
            }
        }

        playImage.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = playImage
                playImage.background = application.getDrawable(R.drawable.focus_border)
            } else {
                playImage.background = application.getDrawable(R.drawable.unfocus_border)
            }
        }

        rewindImage.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = rewindImage
                rewindImage.background = application.getDrawable(R.drawable.focus_border)
            } else {
                rewindImage.background = application.getDrawable(R.drawable.unfocus_border)
            }
        }

        forwardImage.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = forwardImage
                forwardImage.background = application.getDrawable(R.drawable.focus_border)
            } else {
                forwardImage.background = application.getDrawable(R.drawable.unfocus_border)
            }
        }

        seekBar.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                focusedView = seekBar
                seekBar.background = application.getDrawable(R.drawable.focus_border)
            } else {
                seekBar.background = application.getDrawable(R.drawable.unfocus_border)
            }
        }

        selectTracksButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                openTrackSelectorDialog()
            }
        })

    }

    private fun openTrackSelectorDialog() {
        if (!isShowingTrackSelectionDialog
            && TrackSelectionDialog.willHaveContent(trackSelector!!)) {
            isShowingTrackSelectionDialog = true
            val trackSelectionDialog = TrackSelectionDialog.createForTrackSelector(
                trackSelector!!
            )  /* onDismissListener= */
            { dismissedDialog -> isShowingTrackSelectionDialog = false }
            trackSelectionDialog.show(supportFragmentManager,  /* tag= */null)
        }
    }

    private fun initPlayer() {

        httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)

        defaultDataSourceFactory = DefaultDataSourceFactory(
            applicationContext, httpDataSourceFactory
        )

        //A DataSource that reads and writes a Cache.
        cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(simpleCache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

        // Create a player instance.
        mPlayer = trackSelector?.let {
            SimpleExoPlayer.Builder(this).setSeekBackIncrementMs(10000).setSeekForwardIncrementMs(10000)
                .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory)).setTrackSelector(
                    it
                ).build()
        }

        // Bind the player to the view.
        playerView.player = mPlayer

        mPlayer!!.addListener(object: Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if(playbackState == Player.STATE_BUFFERING) {
                    mProgressBar.visibility = View.VISIBLE
                } else {
                    mProgressBar.visibility = View.GONE
                }
            }
        })

        //setting exoplayer when it is ready.
        mPlayer!!.playWhenReady = true

        //Seeks to a position specified in milliseconds in the specified window.
        mPlayer!!.seekTo(0, 0)

        //set repeat mode.
        mPlayer!!.repeatMode = Player.REPEAT_MODE_OFF

        // Set the media source to be played.
        mPlayer!!.setMediaSource(buildMediaSource())

        // Prepare the player.
        mPlayer!!.prepare()
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || mPlayer == null) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }


    private fun releasePlayer() {
        if (mPlayer == null) {
            return
        }
        //release player when done
        mPlayer!!.release()
        mPlayer = null
    }

    //creating mediaSource
    private fun buildMediaSource(): MediaSource {
        // Create a data source factory.
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

        // Create a progressive media source pointing to a stream uri.
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoURL))

        return mediaSource
    }
}
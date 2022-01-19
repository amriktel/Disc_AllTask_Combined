package com.example.kotlintv.fragment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import androidx.preference.PreferenceManager
import com.example.kotlintv.DemoConstant
import com.example.kotlintv.activity.DetailsActivity
import com.example.kotlintv.activity.SignInActivity
import com.example.kotlintv.model.CategoryList
import com.example.kotlintv.model.Movie
import com.example.kotlintv.player.online.*
import com.example.kotlintv.player.player.PlayerActivity
import com.example.kotlintv.presenter.*
import com.example.kotlintv.viewModel.ContinueWatchViewModel
import com.example.kotlintv.viewModel.WatchApplication
import com.example.kotlintv.viewModel.WatchViewModelFactory
import com.google.android.exoplayer2.util.MimeTypes
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.lang.Exception
import java.util.*

class MainFragment : RowsSupportFragment() {
    private val mHandler = Handler()
    private var mBackgroundTimer: Timer? = null
    private var mBackgroundUri: String? = null
    val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())

    private lateinit var routinesViewModelFactory: WatchApplication
    private lateinit var viewModel: ContinueWatchViewModel
    lateinit var cardPresenterLandscape : CardPresenterLandscape
    lateinit var arrayadapter: ArrayObjectAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onActivityCreated(savedInstanceState)
        loadFiveDifferentRows()
        setupEventListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (null != mBackgroundTimer) {
            Log.d(TAG, "onDestroy: " + mBackgroundTimer.toString())
            mBackgroundTimer!!.cancel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        var exampleMap: MutableMap<Any, Any> = mutableMapOf()
        exampleMap = loadMap(requireActivity()) //load map
        exampleMap.forEach { key, value -> println("-----> example map $key = $value") }

        val watchViewModel: ContinueWatchViewModel by requireActivity()?.viewModels {
            WatchViewModelFactory(((activity?.application) as WatchApplication).repository)
        }


        watchViewModel.mContinueWatch.observe(this) {
            Log.d(TAG, "onResume:all data : \n  " + it.toString() + "\n")
            cardPresenterLandscape.getWatchedDuration(it)
            val size = arrayadapter.size()
            arrayadapter.notifyArrayItemRangeChanged(0, size)
        }
    }
    private fun loadFiveDifferentRows() {
        val list = CategoryList.setupMovies()
        val cardPresenterPortrait = CardPresenterPortrait()
        cardPresenterLandscape = CardPresenterLandscape()
        arrayadapter = ArrayObjectAdapter(cardPresenterLandscape)
        val cardPresenterSquare = CardPresenterSquare()
        val bigCardPresenterLand = BigCardPresenterLand(requireContext())
        val bigCardPresenterPortrait = BigCardPresenterPortrait(requireContext())
        val viewPagerLayout = ViewpagerPresenter(requireActivity())
        var i = 0
        while (i < NUM_ROWS) {
            var header:HeaderItem
            if(i!=0) {
                header = HeaderItem(i.toLong(), CategoryList.MOVIE_CATEGORY[i])
            }else{
                header=HeaderItem("")
            }
            var listRowAdapter: ArrayObjectAdapter = when (i) {
                0 -> ArrayObjectAdapter(viewPagerLayout)
                1 -> ArrayObjectAdapter(cardPresenterLandscape)
                2 -> ArrayObjectAdapter(bigCardPresenterLand)
                3 -> ArrayObjectAdapter(cardPresenterSquare)
                4 -> ArrayObjectAdapter(bigCardPresenterPortrait)
                else -> ArrayObjectAdapter(cardPresenterPortrait)
            }
            if(i==0){
                val movie=Movie()
                listRowAdapter.add(movie)
            }else{
                for (j in 0 until NUM_COLS) {
                    listRowAdapter.add(list!![j % 5])
                }
            }
            rowsAdapter.add(ListRow(header, listRowAdapter))
            i++
        }

        adapter = rowsAdapter
    }

    private fun setupEventListeners() {

        onItemViewClickedListener = ItemViewClickedListener()
        onItemViewSelectedListener = ItemViewSelectedListener()
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder ?, item: Any ?,
            rowViewHolder: RowPresenter.ViewHolder ?, row: Row ?) {
            Log.d(TAG, "onItemClicked: ")
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val isSignedIn = sharedPreferences.getBoolean(DemoConstant.SIGN_IN_STATUS,false)
            val uniqueContentId=(item as Movie).uniqueContentId
            val contentName=(item as Movie).title
            val cardImage=(item as Movie).cardImageUrl
            if (!isSignedIn){
                Toast.makeText(context, "You need to login first", Toast.LENGTH_LONG).show()
                startActivity(Intent(context, SignInActivity::class.java))
            } else {
                val indexOfRow = rowsAdapter.indexOf(row)
                val indexOfItem = ((row as ListRow).adapter as ArrayObjectAdapter).indexOf(item)
                Toast.makeText(context, "$indexOfItem-$indexOfRow", Toast.LENGTH_SHORT).show()
                if(indexOfRow==0){
                    openDetails()
                    return
                }
                if (indexOfRow != 1) {
                    if ((rowViewHolder as ListRowPresenter.ViewHolder).selectedPosition / 2 == 1) {
                        startActivity(
                            Intent(context, PlayerActivity::class.java)
                                .putExtra(BUNDLE_TITLE, "Big Bunny")
                                .putExtra(
                                    BUNDLE_URL,
                                    //"http://blueappsoftware.in/layout_design_android_blog.mp4"
                                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                                )
                                .putExtra(BUNDLE_MIME_TYPES, MimeTypes.APPLICATION_MP4)
                                .putExtra("contentName",contentName)
                                .putExtra("uniqueContentId",uniqueContentId)
                                .putExtra("cardImage",cardImage)
                        )
                    } else {
                        startActivity(
                            Intent(context, PlayerActivity::class.java)
                                .putExtra(BUNDLE_TITLE, "Big Bunny")
                                .putExtra(
                                    BUNDLE_URL,
                                    //"http://blueappsoftware.in/layout_design_android_blog.mp4"
                                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

                                )
                                .putExtra(BUNDLE_MIME_TYPES, MimeTypes.APPLICATION_MP4)
                                .putExtra("contentName",contentName)
                                .putExtra("uniqueContentId",uniqueContentId)
                                .putExtra("cardImage",cardImage)
                        )
                    }
                } else {
                    var lastWatchedDuration: String? = null
                    var exampleMap1: MutableMap<Any, Any> = mutableMapOf()
                    exampleMap1 = loadMap(requireContext())
                    //exampleMap1.forEach { (key, value) -> println("-----> $key = $value") }
                    val itr = exampleMap1.keys.iterator()
                    while (itr.hasNext()) {
                        val key = itr.next()
                        val value = exampleMap1[key]
                        Log.d("----->", "key -$key value -$value")
                        Log.d("----->", "indexOfItem -$indexOfItem")
                        if (indexOfItem.toString().equals(key) && value != null) {
                            lastWatchedDuration = value.toString()
                            Log.d("----->", "key -$key value -$value")
                            Log.d("----->", "lastWatchedDuration -$lastWatchedDuration")
                            break
                        }
                    }
                    Log.d("----->", "found value $lastWatchedDuration")
                    startActivity(
                        Intent(context, PlayerActivity::class.java)
                            .putExtra(BUNDLE_TITLE, "Big Bunny")
                            .putExtra(CONTENT_ID, "$indexOfItem")
                            .putExtra(LAST_WATCHED_DURATION, "$lastWatchedDuration")
                            .putExtra(
                                BUNDLE_URL,
                                //"https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8")
                                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                            )
                            .putExtra(BUNDLE_MIME_TYPES, MimeTypes.APPLICATION_MP4)
                            .putExtra("contentName",contentName)
                            .putExtra("uniqueContentId",uniqueContentId)
                            .putExtra("cardImage",cardImage)
                    )
                }
            }
        }
    }

    fun openDetails(){
        val intent = Intent(activity, DetailsActivity::class.java)
        if (context != null) {
            intent.putExtra("showName","Show One")
            intent.putExtra("imageUrl","https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg")
            intent.putExtra("description","In publishing and graphic design, Lorem ipsum is a placeholder text commonly\" +\n" +
                    "                    \" used to demonstrate the visual form of a document or a typeface without relying on meaningful content.\" +\n" +
                    "                    \" Lorem ipsum may be used as a placeholder before the final copy is available")
            intent.putExtra("duration","2hrs")
            intent.putExtra("episode","2")
            activity?.let { ContextCompat.startActivity(it, intent, Bundle()) }
        }
    }
    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder ?,
            item: Any ?,
            rowViewHolder: RowPresenter.ViewHolder ?,
            row: Row ?
        ) {
            if (item is Movie) {
                mBackgroundUri = item.backgroundImageUrl
                //startBackgroundTimer()
            }
        }
    }
    fun loadMap(context: Context): MutableMap<Any, Any> {
        return try {
            val fos: FileInputStream = context.openFileInput("map")
            val os = ObjectInputStream(fos)
            val map: MutableMap<Any, Any> = os.readObject() as MutableMap<Any, Any>
            os.close()
            fos.close()
            map
        } catch (e: Exception) {
            mutableMapOf()
        }
    }
    companion object {
        private const val TAG = "MainFragment"
        private const val BACKGROUND_UPDATE_DELAY = 300
        private const val GRID_ITEM_WIDTH = 200
        private const val GRID_ITEM_HEIGHT = 200
        private const val NUM_ROWS = 6
        private const val NUM_COLS = 5
    }
}
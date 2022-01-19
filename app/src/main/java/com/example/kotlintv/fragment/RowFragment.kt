package com.example.kotlintv.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import androidx.preference.PreferenceManager
import com.example.kotlintv.*
import com.example.kotlintv.activity.SignInActivity
import com.example.kotlintv.database.CWTrayDB
import com.example.kotlintv.database.ContinueWatchTrayTable
import com.example.kotlintv.model.*
import com.example.kotlintv.player.online.*
import com.example.kotlintv.player.player.PlayerActivity
import com.example.kotlintv.presenter.*
import com.google.android.exoplayer2.util.MimeTypes
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.lang.Exception
import java.util.*

class RowFragment(private var pageName: String) : RowsSupportFragment() {

    lateinit var cardPresenterLandscape: ContinueWatchTrayPresenter
    lateinit var rowsAdapter: ArrayObjectAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEventListeners()
    }

    private fun setupEventListeners() {
        onItemViewClickedListener = ItemViewClickedListener()
        onItemViewSelectedListener = ItemViewSelectedListener()
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val isSignedIn = sharedPreferences.getBoolean(DemoConstant.SIGN_IN_STATUS, false)
            var uniqueContentId:String = ""
            var contentName:String = ""
            var cardImage:String = ""
            var lastWatchedDuration: Long=0
            Toast.makeText(context, "${rowsAdapter.indexOf(row)} - ${((row as ListRow).adapter as ArrayObjectAdapter).indexOf(item)} ", Toast.LENGTH_LONG).show()
            if(item is ContinueWatchTrayTable){
                uniqueContentId = (item as ContinueWatchTrayTable).uniqueContentId
                contentName= (item as ContinueWatchTrayTable).title
                 cardImage = (item as ContinueWatchTrayTable).cardImageUrl
                lastWatchedDuration = (item as ContinueWatchTrayTable).lastWatchDuration
                if (!isSignedIn) {
                    Toast.makeText(context, "You need to login first", Toast.LENGTH_LONG).show()
                    startActivity(Intent(context, SignInActivity::class.java))
                } else {
                    val indexOfRow = rowsAdapter.indexOf(row)
                    val indexOfItem = ((row as ListRow).adapter as ArrayObjectAdapter).indexOf(item)
                    Toast.makeText(context, "$indexOfItem-$indexOfRow", Toast.LENGTH_SHORT).show()

                    /*var exampleMap1: MutableMap<Any, Any> = mutableMapOf()
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
                    }*/
                    Log.d("----->", "found value $lastWatchedDuration")
                    startActivity(
                        Intent(context, PlayerActivity::class.java)
                            .putExtra(BUNDLE_TITLE, "Big Bunny")

                                .putExtra(LAST_WATCHED_DURATION, lastWatchedDuration)
                            .putExtra(
                                BUNDLE_URL,
                                //"https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8")
                                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                            )
                            .putExtra(BUNDLE_MIME_TYPES, MimeTypes.APPLICATION_MP4)
                            .putExtra("contentName", contentName)
                            .putExtra("uniqueContentId", uniqueContentId)

                            .putExtra("cardImage", cardImage)

                    )
                }
            }


        }

    }

    /*fun loadMap(context: Context): MutableMap<Any, Any> {
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
    }*/

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            Log.d("Monisha", "onItemSelected: ")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (pageName) {
            "Sports" -> {
                loadSportData()
            }
            /*"Movies" -> {
                loadMovieData()
            }*/
            "Home" -> {
                fetchCWTrayData()
            }
            else -> {
                loadChannelsData()
            }
        }
    }

    private fun fetchCWTrayData() {
        val cwTrayDao = CWTrayDB.getCWTrayDatabase(requireContext())?.getContinueTrayDao()
        cwTrayDao?.getAllData()?.observe(requireActivity(), {
            loadHomeData(it)
        })
    }

    private fun loadChannelsData() {
        val list: List<Channels>? = ChannelsList.setupChannels()
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter(pageName)
        var i = 0
        while (i < 2) {
            if (i != 0) {
                Collections.shuffle(list)
            }
            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            for (j in 0..4) {
                listRowAdapter.add(list?.get(j % 5))
            }
            val header = HeaderItem(i.toLong(), ChannelsList.CHANNELS_CATEGORY[i])
            rowsAdapter.add(ListRow(header, listRowAdapter))
            i++
        }
        adapter = rowsAdapter
    }

    private fun loadHomeData(cwList: List<ContinueWatchTrayTable>) {
        val list: List<Home>? = HomeList.setupHome()
        rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter(pageName)
        cardPresenterLandscape = ContinueWatchTrayPresenter()

        var i = 0
        while (i < 3) {
            if (i != 0) {
                Collections.shuffle(list)
            }
            var listRowAdapter: ArrayObjectAdapter? = null

            if (i == 0) {

                if (cwList != null && cwList.size > 0) {
                    listRowAdapter = ArrayObjectAdapter(cardPresenterLandscape)
                    for (j in 0 until cwList?.size) {
                        listRowAdapter.add(cwList?.get(j))
                    }
                } else {
                    i++
                    continue
                }
            } else {
                listRowAdapter = ArrayObjectAdapter(cardPresenter)
                for (j in 0..4) {
                    listRowAdapter.add(list?.get(j % 5))
                }
            }

            val header = HeaderItem(i.toLong(), HomeList.HOME_CATEGORY[i])
            rowsAdapter.add(ListRow(header, listRowAdapter))
            i++
        }
        adapter = rowsAdapter
    }

    private fun loadMovieData() {
        val list: List<Movie?> = MovieList.setupMovies()
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter(pageName)
        var i = 0
        while (i < 2) {
            if (i != 0) {
                Collections.shuffle(list)
            }
            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            for (j in 0..4) {
                listRowAdapter.add(list[j % 5])
            }
            val header = HeaderItem(i.toLong(), MovieList.MOVIE_CATEGORY[i])
            rowsAdapter.add(ListRow(header, listRowAdapter))
            i++
        }
        adapter = rowsAdapter
    }

    private fun loadSportData() {
        val list: List<Sport>? = SportsList.setupSports()
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter(pageName)
        var i = 0
        while (i < 2) {
            if (i != 0) {
                Collections.shuffle(list)
            }
            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            for (j in 0..4) {
                listRowAdapter.add(list?.get(j % 5))
            }
            val header = HeaderItem(i.toLong(), SportsList.SPORT_CATEGORY[i])
            rowsAdapter.add(ListRow(header, listRowAdapter))
            i++
        }
        adapter = rowsAdapter
    }

}
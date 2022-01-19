package com.example.kotlintv.player

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintv.R
import com.example.kotlintv.player.offline.OfflineVideoActivity
import com.example.kotlintv.player.online.OnlineAdapter
import com.example.kotlintv.player.util.MediaList
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VideoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_list_activity_main)

        val onlineAdapter = OnlineAdapter()
        findViewById<RecyclerView>(R.id.rv_online_video).apply {
            layoutManager = LinearLayoutManager(this@VideoListActivity)
            adapter = onlineAdapter
        }
        onlineAdapter.submitList(MediaList.listMediaItem)
        findViewById<FloatingActionButton>(R.id.fab_my_downloads).setOnClickListener {
            startActivity(Intent(this, OfflineVideoActivity::class.java))
        }
    }
}
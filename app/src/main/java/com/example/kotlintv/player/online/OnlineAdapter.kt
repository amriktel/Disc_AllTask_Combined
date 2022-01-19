package com.example.kotlintv.player.online

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintv.R
import com.example.kotlintv.player.player.PlayerActivity
import com.example.kotlintv.player.util.DiffUtilOnlineAdapter
import com.google.android.exoplayer2.MediaItem


const val BUNDLE_TITLE = "MEDIA_ITEM_TITLE"
const val BUNDLE_URL = "MEDIA_ITEM_URL"
const val BUNDLE_MIME_TYPES = "MEDIA_MIME_TYPES"
const val BUNDLE_TAG = "MEDIA_ITEM_TAG"
const val CONTENT_ID = "CONTENT_ID"
const val LAST_WATCHED_DURATION = "LAST_WATCHED_DURATION"

class OnlineAdapter : ListAdapter<MediaItem , OnlineAdapter.OnlineViewHolder>(DiffUtilOnlineAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnlineViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_online,parent,false)
        return OnlineViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnlineViewHolder, position: Int) {
        val mediaItem : MediaItem = getItem(position)
        holder.bind(mediaItem)
        holder.itemView.setOnClickListener {
                it.context.startActivity(
                    Intent(it.context, PlayerActivity::class.java)
//                    Intent(it.context, VideoPlayerActivity::class.java)
                        .putExtra(BUNDLE_TITLE, mediaItem.mediaMetadata.title ?: "No title")
                        .putExtra(BUNDLE_URL, mediaItem.playbackProperties?.uri.toString())
                        .putExtra(BUNDLE_MIME_TYPES, mediaItem.playbackProperties?.mimeType)
//                    .putExtra(BUNDLE_TAG, mediaItem.playbackProperties?.tag as MediaItemTag)
                )
        }
    }

    class OnlineViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val title : TextView = view.findViewById(R.id.tv_online_title)
        private val url : TextView = view.findViewById(R.id.tv_online_url)
        fun bind(mediaItem: MediaItem){
            title.text=mediaItem.mediaMetadata.title ?: "No Title Set"
            url.text=mediaItem.playbackProperties?.uri.toString()
        }
    }
}

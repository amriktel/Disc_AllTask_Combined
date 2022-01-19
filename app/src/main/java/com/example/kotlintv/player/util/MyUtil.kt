package com.example.kotlintv.player.util

import androidx.recyclerview.widget.DiffUtil
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.util.MimeTypes

object DiffUtilOnlineAdapter : DiffUtil.ItemCallback<MediaItem>() {
    override fun areItemsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean {
        return oldItem.mediaId == newItem.mediaId
    }

    override fun areContentsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean {
        return oldItem==newItem
    }
}

object MediaList{
 val listMediaItem: List<MediaItem> = listOf(
    MediaItem.Builder()
        .setUri("https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8")
        .setMimeType(MimeTypes.APPLICATION_M3U8)
        .setMediaMetadata(MediaMetadata.Builder().setTitle("Google Example").build())
        .build(),
    MediaItem.Builder()
        .setUri("http://demo.unified-streaming.com/video/tears-of-steel/tears-of-steel.ism/.m3u8")
        .setMimeType(MimeTypes.APPLICATION_M3U8)
        .setMediaMetadata(MediaMetadata.Builder().setTitle("Tears of Steel").build())
        .build(),
    MediaItem.Builder()
        .setUri("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8")
        .setMimeType(MimeTypes.APPLICATION_M3U8)
        .setMediaMetadata(MediaMetadata.Builder().setTitle("Animation Movie Example").build())
        .build(),
    MediaItem.Builder()
//        .setUri("http://sample.vodobox.com/big_buck_bunny_4k/big_buck_bunny_4k.m3u8")
        .setUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        .setMimeType(MimeTypes.APPLICATION_MP4)
        .setMediaMetadata(MediaMetadata.Builder().setTitle("Big Bunny").build())
        .build(),
    MediaItem.Builder()
        .setUri("http://sample.vodobox.com/pipe_dream_tahiti/pipe_dream_tahiti.m3u8")
        .setMimeType(MimeTypes.APPLICATION_M3U8)
        .setMediaMetadata(MediaMetadata.Builder().setTitle("Pipe Dream Haiti").build())
        .build(),
    MediaItem.Builder()
        .setUri("http://sample.vodobox.com/caminandes_1_4k/caminandes_1_4k.m3u8")
        .setMimeType(MimeTypes.APPLICATION_M3U8)
        .setMediaMetadata(MediaMetadata.Builder().setTitle("Caminandes 4k").build())
        .build(),
    MediaItem.Builder()
        .setUri("http://sample.vodobox.com/skate_phantom_flex_4k/skate_phantom_flex_4k.m3u8")
        .setMimeType(MimeTypes.APPLICATION_M3U8)
        .setMediaMetadata(MediaMetadata.Builder().setTitle("Skate Phantom 4k").build())
        .build(),
    MediaItem.Builder()
        .setUri("http://sample.vodobox.com/planete_interdite_hevc/planete_interdite_hevc.m3u8")
        .setMimeType(MimeTypes.APPLICATION_M3U8)
        .setMediaMetadata(MediaMetadata.Builder().setTitle("Planete Interdite").build())
        .build(),
    MediaItem.Builder()
        .setUri("http://playertest.longtailvideo.com/adaptive/oceans_aes/oceans_aes.m3u8")
        .setMimeType(MimeTypes.APPLICATION_M3U8)
        .setMediaMetadata(MediaMetadata.Builder().setTitle("Ocean Example").build())
        .build(),
)
}
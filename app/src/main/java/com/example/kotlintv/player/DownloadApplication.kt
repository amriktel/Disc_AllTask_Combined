package com.example.kotlintv.player

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.kotlintv.player.download.MyDownloadService
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.offline.DownloadService
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache

class DownloadApplication: Application() {

    companion object {
        lateinit var simpleCache: SimpleCache
        const val exoPlayerCacheSize: Long = 90 * 1024 * 1024
        lateinit var leastRecentlyUsedCacheEvictor: LeastRecentlyUsedCacheEvictor
        lateinit var exoDatabaseProvider: ExoDatabaseProvider
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(exoPlayerCacheSize)
        exoDatabaseProvider = ExoDatabaseProvider(this)
        simpleCache = SimpleCache(cacheDir, leastRecentlyUsedCacheEvictor, exoDatabaseProvider)

        // Can be moved to Application class
        try {
            DownloadService.start(this, MyDownloadService::class.java)
        } catch (e: IllegalStateException) {
            DownloadService.startForeground(this, MyDownloadService::class.java)
        }
    }
}
package com.example.kotlintv.listeners

import com.example.kotlintv.model.ContinueWatch

interface progressBarCallback {
    fun getWatchedDuration(continueWatchList:List<ContinueWatch>)

}
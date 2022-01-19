package com.example.kotlintv.presenter

import android.content.Context
import android.util.Log
import androidx.leanback.widget.Presenter
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.leanback.widget.ImageCardView
import com.bumptech.glide.Glide
import com.example.kotlintv.R
import com.example.kotlintv.listeners.progressBarCallback
import com.example.kotlintv.model.ContinueWatch
import com.example.kotlintv.model.Movie

class CardPresenterLandscape : Presenter(), progressBarCallback {
    private var mContext: Context? = null
    var progressBar1: ProgressBar? = null
    private var mcontinueWatchList: List<ContinueWatch>? = null
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.landscape_layout, parent, false)
        mContext = parent.context
        val cardView = object : ImageCardView(parent.context) {
            override fun setSelected(selected: Boolean) {
                super.setSelected(selected)
            }
        }
        cardView.requestFocus()
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        return LandscapeCardViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val movie = item as Movie
        val viewHolder1 = viewHolder as LandscapeCardViewHolder
        if (movie.title != null) {
            viewHolder1.title.text = movie.title
        }
        //viewHolder.mainImage.setBackgroundResource(R.drawable.frozen)
        Glide.with(mContext!!).load(movie.cardImageUrl).centerCrop().into(viewHolder.mainImage)

        setProgressBar(viewHolder.progressBar)
        mcontinueWatchList?.let{
            if(mcontinueWatchList!!.isNotEmpty()) {
                for (continueWatch in mcontinueWatchList!!) {
                    if(continueWatch.contentId == item.id.toInt()){
                        setWatchedDuration(viewHolder.progressBar, item.id.toInt(), continueWatch)
                    }
                }
            }
        }
    }

    fun setProgressBar(progressBar: ProgressBar) {
        progressBar1 = progressBar
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {}
    private inner class LandscapeCardViewHolder(view: View) : ViewHolder(view) {
        var mainImage: ImageView
        var title: TextView
        var cardView: CardView
        var progressBar: ProgressBar
        init {
            cardView = view.findViewById(R.id.cardview)
            mainImage = view.findViewById(R.id.main_image)
            title = view.findViewById(R.id.title)
            progressBar = view.findViewById(R.id.card_content_progress)

        }
    }

    fun setWatchedDuration(progressBar: ProgressBar,index:Int,continueWatch: ContinueWatch){
        if(mcontinueWatchList != null && mcontinueWatchList!!.size>0) {

            var progressValue:Long=0
            if(continueWatch.contentId==index){
                if (continueWatch.totalDuration > 0 && continueWatch.lastWatchDuration > 0) {
                    progressValue = (continueWatch.lastWatchDuration * 100 ) / continueWatch.totalDuration
                    progressBar?.visibility = View.VISIBLE
                    progressBar?.setProgress(progressValue.toInt())
                } else if (continueWatch.lastWatchDuration == continueWatch.totalDuration) {
                    progressValue = 100
                    progressBar?.visibility = View.VISIBLE
                    progressBar?.setProgress(progressValue.toInt())
                } else {
                    progressValue = 0
                    progressBar!!.visibility = View.INVISIBLE
                    progressBar?.setProgress(progressValue.toInt())
                }
            }
        }
    }

    override fun getWatchedDuration(continueWatchList:List<ContinueWatch>) {
        mcontinueWatchList=continueWatchList
    }
}
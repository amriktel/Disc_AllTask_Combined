package com.example.kotlintv.presenter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.example.kotlintv.R
import com.example.kotlintv.database.ContinueWatchTrayTable
import com.example.kotlintv.listeners.progressBarCallback
import com.example.kotlintv.model.ContinueWatch
import com.example.kotlintv.model.Movie

class ContinueWatchTrayPresenter : Presenter() {
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
        return CWTrayCardViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val movie = item as ContinueWatchTrayTable
        if (movie == null) return
        val viewHolder1 = viewHolder as CWTrayCardViewHolder
        if (movie.title != null) {
            viewHolder1.title.text = movie.title
        }
//        viewHolder.mainImage.setBackgroundResource(R.drawable.frozen)
        Glide.with(mContext!!).load(movie.cardImageUrl).centerCrop().into(viewHolder.mainImage)
        setProgressBar(viewHolder.progressBar)
        var progressValue = 0L
        try {
            progressValue = (movie.lastWatchDuration * 100 ) / movie.totalDuration
        } catch (e: ArithmeticException){
            e.printStackTrace()
        }
        viewHolder.progressBar?.visibility = View.VISIBLE
        viewHolder.progressBar?.setProgress(progressValue.toInt())
    }

    fun setProgressBar(progressBar: ProgressBar) {
        progressBar1 = progressBar
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {}
    private inner class CWTrayCardViewHolder(view: View) : ViewHolder(view) {
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


}
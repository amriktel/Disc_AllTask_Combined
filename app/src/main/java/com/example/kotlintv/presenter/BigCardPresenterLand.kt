package com.example.kotlintv.presenter

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import androidx.leanback.widget.Presenter
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.leanback.widget.ImageCardView
import com.example.kotlintv.R
import com.example.kotlintv.model.Movie

class BigCardPresenterLand(private val mContext: Context) : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.big_horizontal_item, parent, false)
        val cardView = object : ImageCardView(parent.context) {
            override fun setSelected(selected: Boolean) {
                super.setSelected(selected)
            }
        }
        cardView.requestFocus()
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        return BigCardItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val movieData = item as Movie
        val holder = viewHolder as BigCardItemViewHolder
        if (movieData == null) return
        if (holder.textView != null) holder.textView?.setText(movieData.title)
        if (holder.imageView != null)
            Glide.with(mContext)
            .load(movieData.cardImageUrl)
            .centerCrop()
            .error(R.drawable.movie)
            .into(holder.imageView)
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        Log.d(TAG, "onUnbindViewHolder")
    }

    internal inner class BigCardItemViewHolder(view: View) : ViewHolder(view) {
        var cardView: CardView
        var imageView: ImageView
        var textView: TextView

        init {
            cardView = view.findViewById(R.id.card_view_container)
            imageView = view.findViewById(R.id.movie_image)
            textView = view.findViewById(R.id.movie_title)
        }
    }

    companion object {
        private val TAG = BigCardPresenterLand::class.java.simpleName
    }
}
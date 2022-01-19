package com.example.kotlintv.presenter

import android.content.Context
import androidx.leanback.widget.Presenter
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.leanback.widget.ImageCardView
import com.bumptech.glide.Glide
import com.example.kotlintv.R
import com.example.kotlintv.model.Movie

class CardPresenterSquare : Presenter() {
    private var mContext: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.square_layout, parent, false)
        mContext = parent.context
        val cardView = object : ImageCardView(parent.context) {
            override fun setSelected(selected: Boolean) {
                super.setSelected(selected)
            }
        }
        cardView.requestFocus()
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        return SquareCardViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val movie = item as Movie
        val viewHolder1 = viewHolder as SquareCardViewHolder
        if (movie.title != null) {
            viewHolder1.title.text = movie.title
        }
        //viewHolder.mainImage.setBackgroundResource(R.drawable.frozen_square)
        Glide.with(mContext!!).load(movie.cardImageUrl).centerCrop().error(R.drawable.movie).into(viewHolder.mainImage)


    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {}
    private inner class SquareCardViewHolder(view: View) : ViewHolder(view) {
        var mainImage: ImageView
        var title: TextView
        var cardView: CardView

        init {
            cardView = view.findViewById(R.id.cardview)
            mainImage = view.findViewById(R.id.main_image)
            title = view.findViewById(R.id.title)
        }
    }
}
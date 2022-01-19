package com.example.kotlintv.presenter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
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

/*
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
class CardPresenterPortrait : Presenter() {
    private val mDefaultCardImage: Drawable? = null
    private var mContext: Context? = null

    /* private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        // Both background colors should be set because the view"s background is temporarily visible
        // during animations.
        view.setBackgroundColor(color);
        view.findViewById(R.id.info_field).setBackgroundColor(color);
    }*/
    override fun onCreateViewHolder(parent: ViewGroup): PortraitCardViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.portrait_layout, parent, false)
        mContext = parent.context
        val cardView = object : ImageCardView(parent.context) {
            override fun setSelected(selected: Boolean) {
                super.setSelected(selected)
            }
        }
        cardView.requestFocus()
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        return PortraitCardViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val movie = item as Movie
        val viewHolder1 = viewHolder as PortraitCardViewHolder
        if (movie.title != null) {
            viewHolder1.title.text = movie.title
        }
        //viewHolder.mainImage.setBackgroundResource(R.drawable.avatar)
        Glide.with(mContext!!).load(movie.cardImageUrl).centerCrop().into(viewHolder.mainImage)

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        Log.d(TAG, "onUnbindViewHolder")
    }

    inner class PortraitCardViewHolder(view: View) : ViewHolder(view) {
        var mainImage: ImageView
        var title: TextView
        var cardView: CardView

        init {
            cardView = view.findViewById(R.id.cardview)
            mainImage = view.findViewById(R.id.main_image)
            title = view.findViewById(R.id.title)
        }
    }

    companion object {
        private const val TAG = "CardPresenter"
        private const val CARD_WIDTH = 313
        private const val CARD_HEIGHT = 176
        private const val sSelectedBackgroundColor = 0
        private const val sDefaultBackgroundColor = 0
    }
}
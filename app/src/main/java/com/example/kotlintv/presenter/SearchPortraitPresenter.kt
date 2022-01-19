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
import com.example.example.Results
import com.example.kotlintv.R

/*
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
class SearchPortraitPresenter : Presenter() {
    private val mDefaultCardImage: Drawable? = null
    private var mContext: Context? = null
    private val imagebaseurl="http://image.tmdb.org/t/p/w500/"

    override fun onCreateViewHolder(parent: ViewGroup): PortraitCardViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_card_layout, parent, false)
        mContext = parent.context
        return PortraitCardViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val results = item as Results

        val viewHolder1 = viewHolder as PortraitCardViewHolder
        if (results.title != null) {
            viewHolder1.title.text = results.title
        }
        val imageurl= imagebaseurl+ results.posterPath
      //  viewHolder.mainImage.setBackgroundResource()

        Glide.with(mContext!!)
                .load(imageurl)
                .error(R.drawable.movie)
                .into(viewHolder.mainImage)
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

    }
}
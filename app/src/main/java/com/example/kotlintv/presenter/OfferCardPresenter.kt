package com.example.kotlintv.presenter

import android.annotation.SuppressLint
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
import androidx.core.content.ContextCompat
import androidx.leanback.widget.ImageCardView
import com.example.kotlintv.R
import com.example.kotlintv.model.Movie
import com.example.kotlintv.model.Offer
import org.w3c.dom.Text
import kotlin.properties.Delegates

/*
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
class OfferCardPresenter : Presenter() {
    private var mContext: Context? = null

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup): OfferCardViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.offer_wall_card_view_layout, parent, false)
        mContext = parent.context

        return OfferCardViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val offer = item as Offer
        val viewHolder1 = viewHolder as OfferCardViewHolder
        viewHolder1.offerTag.text = offer.tag
        viewHolder1.offerText.text = offer.title
        viewHolder1.offerDes.text = offer.description
        viewHolder1.validDate.text = offer.validDate

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        Log.d(TAG, "onUnbindViewHolder")
    }

    inner class OfferCardViewHolder(view: View) : ViewHolder(view) {

        var cardView: CardView
        var offerTag: TextView
        var offerText : TextView
        var offerDes: TextView
        var validDate : TextView


        init {
            cardView = view.findViewById(R.id.offer_card)
            offerTag = view.findViewById(R.id.discount_tag)
            offerText = view.findViewById(R.id.discount_text)
            offerDes = view.findViewById(R.id.discount_description)
            validDate = view.findViewById(R.id.valid_date)
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
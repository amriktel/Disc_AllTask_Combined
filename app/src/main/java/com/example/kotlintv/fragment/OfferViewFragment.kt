package com.example.kotlintv.fragment

import android.app.AlertDialog
import com.example.kotlintv.model.OfferList.setupOfferss
import androidx.leanback.app.VerticalGridSupportFragment
import android.os.Bundle
import com.example.kotlintv.fragment.OfferViewFragment.ItemClickedListener
import com.example.kotlintv.presenter.OfferCardPresenter
import com.example.kotlintv.model.Offer
import android.content.Intent
import android.widget.Toast
import androidx.leanback.widget.*
import com.example.kotlintv.activity.MainActivity
import com.example.kotlintv.player.player.PlayerActivity
import kotlinx.coroutines.newFixedThreadPoolContext

class OfferViewFragment : VerticalGridSupportFragment() {
    private var mAdapter: ArrayObjectAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAdapter()
        onItemViewClickedListener = ItemClickedListener()
    }

    private fun setupAdapter() {
        val gridPresenter = VerticalGridPresenter(2, false)
        gridPresenter.numberOfColumns = 4
        setGridPresenter(gridPresenter)
        val offerCardPresenter = OfferCardPresenter()
        mAdapter = ArrayObjectAdapter(offerCardPresenter)
        adapter = mAdapter
        val offerList = setupOfferss()
        for (offer in offerList!!) {
            mAdapter!!.add(offer)
        }
    }

    private inner class ItemClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder ?,
            item: Any ?,
            rowViewHolder: RowPresenter.ViewHolder ?,
            row: Row ?
        ) {
            val (_, title) = item as Offer
            title
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle("Success")
            alertDialog.setMessage(" $title Offer Applied Successfully")
            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE, "OK"
            ) { dialog, which ->
                dialog.dismiss()
                activity!!.finish()
                context?.startActivity(
                    Intent(context, MainActivity::class.java))
            }
            alertDialog.show()
        }
    }
}
package com.example.kotlintv.presenter

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.leanback.widget.Presenter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlintv.R
import com.example.kotlintv.model.Movie
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import java.util.*


class ViewpagerPresenter(private val  mainFragment: FragmentActivity) : Presenter() {

    private var mContext: Context? = null
    private lateinit var fragmentActivity:FragmentActivity
    private val NUM_PAGES = 5
    private lateinit var viewPager : ViewPager2
    private lateinit var dotsIndicator : DotsIndicator
    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 500 //delay in milliseconds before task is to be executed
    val PERIOD_MS: Long = 3000 // time in milliseconds between successive task executions.

    init{
        fragmentActivity=mainFragment

    }
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.viewpager_layout, parent, false)
        mContext=parent?.context
        val relativeLayout: RelativeLayout =view.findViewById(R.id.relative_layout)
        relativeLayout.isFocusable=true
        relativeLayout.isFocusableInTouchMode=true
        relativeLayout.requestFocus()

        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter()
        viewPager.adapter = pagerAdapter
        dotsIndicator.setViewPager2(viewPager)

       /* After setting the adapter use the timer
        After setting the adapter use the timer*/
        val handler = Handler()
        val Update = Runnable {
            if (currentPage === NUM_PAGES ) {
                currentPage = 0
            }
            viewPager.setCurrentItem(currentPage++, true)
        }

        timer = Timer() // This will create a new Thread

        timer!!.schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(Update)
            }
        }, DELAY_MS, PERIOD_MS)

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }


    inner class ViewPagerViewHolder(view: View) : Presenter.ViewHolder(view) {

            init {
                viewPager= view.findViewById(R.id.pager)
                dotsIndicator= view.findViewById(R.id.dots_indicator)
               // setViewPager()
                //setIndicators()
            }

    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter() : RecyclerView.Adapter<ScreenSlidePagerAdapter.PagerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
            val  view = LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item, parent, false)
            return PagerViewHolder(view)
        }

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            if (position == 1) {
                holder.imageView.setImageResource(R.drawable.dis2)
            } else if (position == 2) {
                holder.imageView.setImageResource(R.drawable.dis21)
            } else if (position == 3) {
                holder.imageView.setImageResource(R.drawable.dis3)
            }else {
                holder.imageView.setImageResource(R.drawable.dis2)
            }
        }

        override fun getItemCount(): Int {
            return NUM_PAGES
        }

        private inner class PagerViewHolder(view: View):RecyclerView.ViewHolder(view){
            lateinit var imageView: ImageView
            init{
                 imageView=view.findViewById<ImageView>(R.id.imageview)
            }
        }
    }


}
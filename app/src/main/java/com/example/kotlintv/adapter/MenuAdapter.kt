package com.example.kotlintv.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintv.MenuClickListener
import com.example.kotlintv.R

class MenuAdapter(
    private val mContext : Context,
    private val mList: ArrayList<String>,
    private val menuClickListener: MenuClickListener
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val llMenuMain: LinearLayout = itemView.findViewById(R.id.menu_main)
        val menuText: TextView = itemView.findViewById(R.id.menu_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.menuText.text = mList[position]

        holder.llMenuMain.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                holder.menuText.startAnimation(
                    AnimationUtils.loadAnimation(
                        mContext,
                        android.R.anim.slide_in_left
                    ))
                holder.menuText.setTextColor(Color.BLACK)
            } else {
                holder.menuText.setTextColor(Color.WHITE)
            }
        }

        holder.llMenuMain.setOnClickListener {
            menuClickListener.onMenuClick(holder.menuText.text as String)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}
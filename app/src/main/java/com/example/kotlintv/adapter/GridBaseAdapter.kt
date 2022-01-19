package com.example.kotlintv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlintv.DemoConstant
import com.example.kotlintv.R
import com.example.kotlintv.model.Movie

class GridBaseAdapter(private val mContext: Context, private var mList: ArrayList<Movie>) : RecyclerView.Adapter<GridBaseAdapter.GridViewHolder>() {

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardViewParent: CardView = itemView.findViewById(R.id.cardview)
        val imageView: ImageView  = itemView.findViewById(R.id.main_image)
        val titleView: TextView  = itemView.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.portrait_layout, parent, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {

        holder.cardViewParent.onFocusChangeListener = View.OnFocusChangeListener {_, hasFocus ->
            if(hasFocus) {
                holder.titleView.setBackgroundColor(mContext.resources.getColor(R.color.blue_focused))
                holder.titleView.setTextColor(mContext.resources.getColor(R.color.white))
            } else {
                holder.titleView.setBackgroundColor(mContext.resources.getColor(R.color.selected_background))
                holder.titleView.setTextColor(mContext.resources.getColor(R.color.lb_basic_card_bg_color))
            }
        }
        holder.cardViewParent.setOnClickListener {
            holder.cardViewParent.isFocusable = true
            holder.cardViewParent.requestFocus()
            Toast.makeText(mContext,mList[position].title, Toast.LENGTH_SHORT).show()
        }

        holder.titleView.text = mList[position].title
        Glide.with(mContext)
            .load(mList[position].cardImageUrl)
            .centerCrop()
            .error(mContext.getDrawable(R.drawable.movie))
            .into(holder.imageView)

    }

    override fun getItemCount(): Int = mList.size

    fun setList(list: ArrayList<Movie>){
        this.mList = list
    }
}
package com.example.kotlintv.adapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.leanback.animation.LogDecelerateInterpolator
import com.bumptech.glide.Glide
import com.example.kotlintv.R
import com.example.kotlintv.activity.DetailsActivity
import com.example.kotlintv.fragment.WatchlistFragment
import com.example.kotlintv.listeners.EditClickListener
import com.example.kotlintv.model.GuideInfoList

class WatchlistAdapter(val watchlist: List<GuideInfoList>, val mContext: FragmentActivity) : BaseAdapter(), EditClickListener{

    var title: TextView?=null
    var isEditEnabled:Boolean=false
    var listOfId:ArrayList<String>?=ArrayList<String>()

    override fun getCount(): Int {
        return watchlist.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if (convertView == null) {

           convertView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.watchlist_card_item, parent, false)
        }
        val itemImage:ImageView? =convertView?.findViewById(R.id.item_image)
        val title:TextView? = convertView?.findViewById(R.id.title)
        val linearLayout:RelativeLayout?= convertView?.findViewById(R.id.lin_layout)
        val deleteImage:ImageView?=convertView?.findViewById(R.id.delete_image)
        title?.text=watchlist?.get(position)?.showName
        itemImage?.let {
            Glide.with(mContext!!)
                .load(watchlist?.get(position)?.cardImageUrl)
                .error(R.drawable.movie)
                .into(it)
        }
        linearLayout?.setOnFocusChangeListener { v, hasFocus ->
           if(hasFocus){
               linearLayout?.background=mContext.resources.getDrawable(R.drawable.focussed_card)
           }else{
               linearLayout?.background=mContext.resources.getDrawable(R.drawable.unfocus_border)
           }
       }
        if(isEditEnabled){
            linearLayout?.alpha=0.5F
        }else{
            linearLayout?.alpha=1.0F
        }
        linearLayout?.setOnClickListener({
            if(isEditEnabled){
                if(deleteImage?.visibility == View.VISIBLE){
                    deleteImage?.visibility=View.GONE
                    listOfId?.remove(watchlist.get(position).id)
                }else{
                    deleteImage?.visibility=View.VISIBLE
                    listOfId?.add(watchlist.get(position).id)
                }
            }else{
                val intent = Intent(mContext, DetailsActivity::class.java)
                intent.putExtra("id",watchlist.get(position).id)
                intent.putExtra("showName",watchlist.get(position).showName)
                intent.putExtra("imageUrl",watchlist.get(position).cardImageUrl)
                intent.putExtra("description","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book")
                mContext?.let { ContextCompat.startActivity(it, intent, Bundle()) }

            }
        })

        return convertView
    }

    override fun isEditEnabled(enabled: Boolean) {
        this.isEditEnabled=enabled
    }

    override fun isDeleted(deleted: Boolean) {

    }

    fun getDeleteList(): ArrayList<String>? {
        return listOfId;
    }
}
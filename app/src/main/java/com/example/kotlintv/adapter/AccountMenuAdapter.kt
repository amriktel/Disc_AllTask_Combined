package com.example.kotlintv.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintv.DemoConstant
import com.example.kotlintv.R

class AccountMenuAdapter(private val mContext: Context,
                         private val mListTitle: ArrayList<String>,
                         private val mListIcon: ArrayList<Drawable>):
    RecyclerView.Adapter<AccountMenuAdapter.ViewHolder>() {

    private var mParams: LinearLayout.LayoutParams? = null

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val mIcon: ImageView = itemView.findViewById(R.id.account_icon)
        val mTitle: TextView = itemView.findViewById(R.id.account_text)
        val mParentLayout: LinearLayout = itemView.findViewById(R.id.parent_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_menu_item, parent, false)
        mParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        mParams!!.marginStart = 0
        mParams!!.gravity = Gravity.CENTER
        return ViewHolder(view)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTitle.text = mListTitle[position]
        holder.mIcon.setImageDrawable(mListIcon[position])
        holder.mTitle.layoutParams = mParams
        holder.mTitle.setTextColor(mContext.resources.getColor(R.color.white))
        holder.mIcon.visibility = View.VISIBLE
        holder.mTitle.textSize = 15f

        holder.mParentLayout.setOnClickListener{
            accountItemClickListener.onAccountItemClicked(holder.mTitle.text as String)
        }

        holder.mParentLayout.onFocusChangeListener = View.OnFocusChangeListener {_, hasFocus ->
            if(hasFocus) {
                holder.mParentLayout.background = mContext.getDrawable(R.drawable.focused_bg)
                holder.mTitle.setTextColor(mContext.resources.getColor(R.color.black))
                when(holder.mTitle.text) {
                    DemoConstant.SIGN_OUT -> holder.mIcon.post {
                        holder.mIcon.setImageDrawable(mContext.getDrawable(R.drawable.ic_signout))
                    }
                    DemoConstant.PACK_DETAILS -> holder.mIcon.post {
                        holder.mIcon.setImageDrawable(mContext.getDrawable(R.drawable.ic_cart))
                    }
                }
            } else {
                holder.mParentLayout.setBackgroundColor(mContext.resources.getColor(R.color.black_two))
                holder.mTitle.setTextColor(mContext.resources.getColor(R.color.white))
                when(holder.mTitle.text) {
                    DemoConstant.SIGN_OUT -> holder.mIcon.post {
                        holder.mIcon.setImageDrawable(mContext.getDrawable(R.drawable.ic_signout))
                    }
                    DemoConstant.PACK_DETAILS -> holder.mIcon.post {
                        holder.mIcon.setImageDrawable(mContext.getDrawable(R.drawable.ic_cart))
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return mListTitle.size
    }

    private lateinit var accountItemClickListener: AccountItemClickListener
    fun setClickListener(accountItemClickListener: AccountItemClickListener){
        this.accountItemClickListener = accountItemClickListener
    }

    interface AccountItemClickListener{
        fun onAccountItemClicked(title: String)
    }

}

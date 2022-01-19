package com.example.kotlintv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintv.DemoConstant
import com.example.kotlintv.R
import com.example.kotlintv.activity.AccountDetailsActivity
import com.example.kotlintv.fragment.AddProfileFragment
import com.example.kotlintv.model.MultiProfile
import java.lang.Exception

class ProfileAdapter(context: Context?) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {
    private var mContext: Context? = context;
    private var userName: List<MultiProfile>? = null;
    private lateinit var profileListener: AccountDetailsActivity.ProfileListener;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_item, parent, false)
        return ProfileAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.linearLayout.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                holder.linearLayout.background = mContext?.getDrawable(R.drawable.avatar)
            } else {
                mContext?.resources?.let { holder.linearLayout.setBackgroundColor(it?.getColor(R.color.black_two)) }
            }
        }

        if (userName != null && userName!!.size > position) {
            holder.profileNameText.text = userName!![position].profileName;
            holder.profileNameText.isVisible = true;
            holder.profilePic.isVisible = true;
            holder.addImg.isVisible = false;
            holder.profilePic.background = mContext?.getDrawable(R.drawable.defaultprofile)
            holder.addProfileCard.setOnClickListener(null);
            if(userName!![position].profileType == 1) {
                holder.profileType.text = "Kids(${userName!![position].ageGroup}) ";
                holder.profileType.isVisible = true;
            } else {
                holder.profileType.text = "";
                holder.profileType.isVisible = false;
            }
        } else {
            holder.profileNameText.isVisible = false;
            holder.profilePic.isVisible = false;
            holder.addImg.isVisible = true;
            holder.profileType.isVisible = false;
            holder.addProfileCard.setOnClickListener(View.OnClickListener {
                profileListener.onCreateProfileClicked();
            })
        }

        holder.linearLayout.onFocusChangeListener = View.OnFocusChangeListener {_, hasFocus ->
            if(hasFocus) {
                holder.linearLayout.background = mContext?.getDrawable(R.drawable.focused_bg)
            } else {
                mContext?.resources?.let { holder.linearLayout.setBackgroundColor(it?.getColor(R.color.black_two)) }
            }
        }
    }

    override fun getItemCount(): Int {
        return 5;
    }

    fun updateProfileData(userName: List<MultiProfile>) {
        this.userName = userName;
        notifyDataSetChanged();
    }

    fun setProfileListener(profileListener: AccountDetailsActivity.ProfileListener) {
        this.profileListener = profileListener;
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var linearLayout: LinearLayout = itemView.findViewById(R.id.main_profile_container);
        var profileNameText: TextView = itemView.findViewById(R.id.text_name);
        var profilePic: ImageView = itemView.findViewById(R.id.profile_pic);
        var addImg: ImageView = itemView.findViewById(R.id.add);
        var addProfileCard: CardView = itemView.findViewById(R.id.portrait_card);
        var profileType: TextView = itemView.findViewById(R.id.overlay_text);
    }
}
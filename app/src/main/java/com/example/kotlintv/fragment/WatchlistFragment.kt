package com.example.kotlintv.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.kotlintv.DemoConstant
import com.example.kotlintv.R
import com.example.kotlintv.activity.SignInActivity
import com.example.kotlintv.adapter.WatchlistAdapter
import com.example.kotlintv.database.MyListDB
import com.example.kotlintv.database.MyListDao
import com.example.kotlintv.listeners.EditClickListener
import com.example.kotlintv.model.GuideInfoList
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WatchlistFragment : Fragment(){
   lateinit var watchlistAdapter:WatchlistAdapter
   lateinit var gridView: GridView
   lateinit var editButton:Button
   lateinit var watchlist:List<GuideInfoList>
   lateinit var mEditClickListener:EditClickListener
    private lateinit var myListDao: MyListDao
    private  var isEditEnabled:Boolean = false
    private lateinit var emptyText:TextView
    private lateinit var selectText:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.watchlist_layout,container,false)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val isSignedIn = sharedPreferences.getBoolean(DemoConstant.SIGN_IN_STATUS, false)
        if (!isSignedIn) {
            Toast.makeText(context, "You need to login first", Toast.LENGTH_LONG).show()
            startActivity(Intent(context, SignInActivity::class.java))
        }
        gridView=view.findViewById(R.id.gridview)
        editButton=view.findViewById(R.id.button)
        emptyText=view.findViewById(R.id.empty_text)
        selectText=view.findViewById(R.id.select_text)
        editButton.isFocusable=true
        editButton.setOnClickListener{
            if(!isEditEnabled){
                selectText.visibility=View.VISIBLE
                isEditEnabled=true
                editButton.text="Done"
                mEditClickListener?.isEditEnabled(true)
                watchlistAdapter.notifyDataSetChanged()
            }else{
                selectText.visibility=View.GONE
                editButton.text="Edit"
                isEditEnabled=false
               val idList:ArrayList<String>? = watchlistAdapter.getDeleteList()
                mEditClickListener?.isEditEnabled(false)
                if(!idList.isNullOrEmpty()){
                    deleteItems(idList)
                }else{
                    watchlistAdapter.notifyDataSetChanged()
                }

            }

        }
        editButton.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                editButton.setBackgroundResource(R.drawable.focussed_card)
                editButton.setTextColor(Color.WHITE)
            }else{
                editButton.setTextColor(Color.BLACK)
                editButton.setBackgroundResource(R.drawable.unfocus_border)
            }
        }

        myListDao = MyListDB.getMyListDatabase(requireContext())?.getMyListDao()!!

        return view
    }

    override fun onResume() {
        super.onResume()
        getWatchlistData()
    }

    private  fun deleteItems(idList: ArrayList<String>) {
        Log.d("adapterlog", "id lis: --"+idList.toString())

        GlobalScope.launch {
            for(element in idList) {
                val deleted=myListDao.removeWatchList(element)
                Log.d("adapterlog", "deleted --"+deleted)
            }
            Handler(Looper.getMainLooper()).postDelayed({
                getWatchlistData()
            },1000)
        }
    }

    @DelicateCoroutinesApi
    private fun getWatchlistData() {
        // call getAll watchlist here
        GlobalScope.launch {
            val dataList:List<GuideInfoList> = myListDao.getAllData()
                Handler(Looper.getMainLooper()).post {
                    updateUi(dataList)
                }

        }

    }

    private fun updateUi(dataList: List<GuideInfoList>) {
        if(dataList.isNullOrEmpty()){
            emptyText.visibility = View.VISIBLE
            gridView.visibility=View.GONE
            editButton.visibility=View.GONE
        }else {
            emptyText.visibility = View.GONE
            gridView.visibility=View.VISIBLE
            editButton.visibility=View.VISIBLE
            watchlistAdapter= WatchlistAdapter(dataList,requireActivity())
            gridView.adapter=watchlistAdapter
            watchlistAdapter.notifyDataSetChanged()
            mEditClickListener=watchlistAdapter
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
      isEditEnabled=false
    }
}

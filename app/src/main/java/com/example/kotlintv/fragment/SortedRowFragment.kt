package com.example.kotlintv.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.FOCUSABLE
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.leanback.widget.ImageCardView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintv.DemoConstant
import com.example.kotlintv.R
import com.example.kotlintv.adapter.GridBaseAdapter
import com.example.kotlintv.model.CategoryList
import com.example.kotlintv.model.Movie
import java.util.ArrayList

class SortedRowFragment : Fragment() {

    private lateinit var metaList: ArrayList<Movie>
    private lateinit var gridViewLayout: RecyclerView
    private lateinit var sortSwitch: Switch
    private lateinit var progressSort: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        metaList = CategoryList.setupMultipleMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sorted_row, container, false)
        gridViewLayout = view.findViewById(R.id.grid_view_sort)
        sortSwitch = view.findViewById(R.id.sort_switch)
        progressSort = view.findViewById(R.id.progress_sort)

        val layoutManager = GridLayoutManager(requireContext(), 5)
        gridViewLayout.layoutManager = layoutManager

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val isChecked = sharedPreferences.getBoolean(DemoConstant.SORTING_STATUS, false)
        sortSwitch.isChecked = isChecked
        if (isChecked){
            metaList.sort()
        }

        val gridBaseAdapter = GridBaseAdapter(requireContext(), metaList)
        gridViewLayout.adapter = gridBaseAdapter
        gridViewLayout.addItemDecoration(GridItemDecoration(25))
        sortSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            progressSort.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                if (isChecked){
                    val sortedList = metaList
                    sortedList.sort()
                    gridBaseAdapter.setList(sortedList)
                }else{
                    metaList = CategoryList.setupMultipleMovies()
                    gridBaseAdapter.setList(metaList)
                }
                gridBaseAdapter.notifyDataSetChanged()
                progressSort.visibility = View.GONE
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                val editor = sharedPreferences.edit()
                editor.putBoolean(DemoConstant.SORTING_STATUS, isChecked)
                editor.apply()
            }, 1000)

        }



        return view
    }

    inner class GridItemDecoration(private val space: Int) : RecyclerView.ItemDecoration(){

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = space
            outRect.top = space
            outRect.left = space
            outRect.right = space
        }
    }

}

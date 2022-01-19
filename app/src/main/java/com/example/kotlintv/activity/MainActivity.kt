package com.example.kotlintv.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.leanback.widget.VerticalGridView
import androidx.preference.PreferenceManager
import com.example.kotlintv.DemoConstant
import com.example.kotlintv.adapter.MenuAdapter
import com.example.kotlintv.MenuClickListener
import com.example.kotlintv.R
import com.example.kotlintv.fragment.*
import com.example.kotlintv.viewModel.ContinueWatchViewModel
import com.example.kotlintv.viewModel.WatchApplication
import com.example.kotlintv.viewModel.WatchViewModelFactory


class MainActivity : FragmentActivity(), MenuClickListener {

    private val watchViewModel: ContinueWatchViewModel by viewModels {
        WatchViewModelFactory((application as WatchApplication).repository)
    }
    private lateinit var header: TextView
    private lateinit var mMenuView: VerticalGridView
    private lateinit var mMenuAdapter: MenuAdapter
    private val mMenuList: ArrayList<String> =
        arrayListOf("Profile", "Home", "Movies", "A-Z", "Channels","Watchlist", "Guide", "Search", "Offers")
    private lateinit var mMenuClickListener: MenuClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        mMenuAdapter = MenuAdapter(this, mMenuList, mMenuClickListener)
        mMenuView.adapter = mMenuAdapter
        if (savedInstanceState == null) {
            openFragment("Home")
        }
    }


    private fun init() {
        header = findViewById(R.id.header)
        mMenuView = findViewById(R.id.menu_adapter)
        mMenuClickListener = this
    }


    private fun openFragment(page: String) {
        header.text = page
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_browse_fragment, RowFragment(page))
            .commit()
    }

    private fun openMovieFragment() {
        header.text = getString(R.string.string_movies)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_browse_fragment, MainFragment())
            .commit()
    }

    private fun openGuide() {
        header.text = getString(R.string.upcoming)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_browse_fragment, GuideFragment()).commit()
    }

    private fun openSearch() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    private fun openSignIn() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val isSignedIn = sharedPreferences.getBoolean(DemoConstant.SIGN_IN_STATUS, false)
        val intent: Intent = if (isSignedIn) {
            Intent(this, AccountDetailsActivity::class.java)
        } else {
            Intent(this, SignInActivity::class.java)

        }
        startActivity(intent)
    }

    private fun openOffersFragment() {
        header.text = getString(R.string.string_Offers)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_browse_fragment, ActivateOfferFragment())
            .commit()
    }

    private fun openSortedRowFragment(page: String) {
        header.text = page
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_browse_fragment, SortedRowFragment())
            .commit()
    }

    private fun openWatchlistFragment() {
        //  header.text = getString(R.string.upcoming)
        header.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_browse_fragment, WatchlistFragment()).commit()
    }

    override fun onMenuClick(page: String) {
        when (page) {
            "Guide" -> {
                openGuide()
            }
            "Movies" -> {
                openMovieFragment()
            }
            "A-Z" -> {
                openSortedRowFragment("A-Z")
            }
            "Search" -> {
                openSearch()
            }
            "Profile" -> {
                openSignIn()
            }
            "Offers" -> {
                openOffersFragment()
            }
            "Watchlist" -> {
                openWatchlistFragment()
            }
            else -> {
                openFragment(page)
            }
        }
    }

}
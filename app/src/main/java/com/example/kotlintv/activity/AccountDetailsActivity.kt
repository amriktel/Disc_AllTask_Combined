package com.example.kotlintv.activity

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.leanback.widget.ListRowView
import androidx.leanback.widget.VerticalGridView
import androidx.preference.PreferenceManager
import com.example.kotlintv.DemoConstant
import com.example.kotlintv.R
import com.example.kotlintv.adapter.AccountMenuAdapter
import com.example.kotlintv.adapter.ProfileAdapter
import com.example.kotlintv.database.MultiProfileDatabase
import com.example.kotlintv.database.UserProfileDB
import com.example.kotlintv.fragment.AddProfileFragment
import com.example.kotlintv.fragment.MyAccountFragment
import com.example.kotlintv.model.MultiProfile
import java.util.concurrent.Executors

class AccountDetailsActivity : FragmentActivity(), AccountMenuAdapter.AccountItemClickListener {

    private lateinit var mTitle: TextView
    private lateinit var mName: TextView
    private lateinit var mEmailId: TextView
    private lateinit var mRecycler: VerticalGridView
    private lateinit var mAccountMenuAdapter: AccountMenuAdapter
    private lateinit var  listRowView : ListRowView;
    private lateinit var profileAdapter : ProfileAdapter;
    private var addProfileFragment: AddProfileFragment? = null;
    private var userId: String = "";
    private val mListTitle: ArrayList<String> = arrayListOf(
        DemoConstant.MY_ACCOUNT,
        DemoConstant.PACK_DETAILS,
        DemoConstant.SIGN_OUT
    )
    private lateinit var mListIcon: ArrayList<Drawable>
    private var myAccountFragment: MyAccountFragment? = null

    var profileListener: ProfileListener = object : ProfileListener {
        override fun onCreateProfileClicked() {
            openProfileFragment();
        }

        override fun onNewProfileCreated(profileName: String, profileType: Int, ageGroup: String) {
            if (addProfileFragment != null) {
                supportFragmentManager.beginTransaction().remove(addProfileFragment!!).commit();
                findViewById<FrameLayout>(R.id.fragment_parent).visibility = View.GONE;
                addProfileFragment = null;
            }
            Executors.newSingleThreadExecutor().execute(Runnable {
                val multiProfileDao = applicationContext?.let { it1 -> MultiProfileDatabase.getDatabase(it1).MultiProfileDao() }
                multiProfileDao?.insertAll(MultiProfile(userId, profileName, profileType, ageGroup));
                getMultiProfileData(mName.text.toString());
            })
        }

        override fun describeContents(): Int {
            return 0;
        }

        override fun writeToParcel(p0: Parcel?, p1: Int) {
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)
        initView()
        mListIcon = arrayListOf(
            resources.getDrawable(R.drawable.my_account, null),
            resources.getDrawable(R.drawable.ic_cart, null),
            resources.getDrawable(R.drawable.ic_signout, null))
        mAccountMenuAdapter = AccountMenuAdapter(this, mListTitle, mListIcon)
        mAccountMenuAdapter.setClickListener(this)
        mRecycler.setItemSpacing(10)
        mRecycler.adapter = mAccountMenuAdapter
        profileAdapter = ProfileAdapter(this);
        profileAdapter.setProfileListener(profileListener);
        listRowView.gridView.adapter = profileAdapter;
        listRowView.gridView.setNumRows(1);
        listRowView.isFocusable = true;
        listRowView.isFocusableInTouchMode = true;
        listRowView.get(0).requestFocus();
        profileAdapter.notifyDataSetChanged();

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        userId = sharedPreferences.getString(DemoConstant.SIGNED_IN_USER_ID,"").toString()

        setNameAndEmail(userId.toString(), "User_$userId")

    }

    private fun initView() {
        mTitle = findViewById(R.id.account_title)
        mName = findViewById(R.id.user_name)
        mEmailId = findViewById(R.id.email_id)
        mRecycler = findViewById(R.id.account_setting_list)
        listRowView = findViewById(R.id.row_view);
    }

    private fun setNameAndEmail(userId: String, defaultName: String){
        mName.text = defaultName
        mEmailId.text = getString(R.string.text_emailId)
        Executors.newSingleThreadExecutor().execute(Runnable {
            getMultiProfileData(mName.text.toString());
        });
        UserProfileDB.getUserDatabase(this)?.getUserProfileDao()?.getUserDetail(userId)
            ?.observe(this, {
                it?.let {
                    if (it.user_name.isNotEmpty()) {
                        mName.text = it.user_name
                        Executors.newSingleThreadExecutor().execute(Runnable {
                            getMultiProfileData(it.user_name);
                        });
                    }
                    if (it.user_email.isNotEmpty()) mEmailId.text = it.user_email
                }
            })
    }

    override fun onAccountItemClicked(title: String) {
        when(title) {
            DemoConstant.MY_ACCOUNT -> {
                findViewById<FrameLayout>(R.id.fragment_parent).visibility = View.VISIBLE
                val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                myAccountFragment = MyAccountFragment()
                transaction.replace(R.id.fragment_parent, myAccountFragment!!)
                transaction.commit()
            }
            DemoConstant.SIGN_OUT -> {
                setSignInStatus()
                Toast.makeText(this, "Signed out successfully", Toast.LENGTH_LONG).show()
                finish()
            }
            DemoConstant.PACK_DETAILS -> Toast.makeText(this, "Pack Details", Toast.LENGTH_LONG).show()
        }
    }

    private fun setSignInStatus() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val editor = sharedPreferences.edit()
        editor.putBoolean(DemoConstant.SIGN_IN_STATUS, false)
        editor.putString(DemoConstant.SIGNED_IN_USER_ID, "")
        editor.apply()
    }

    override fun onBackPressed() {
        if (myAccountFragment != null){
            supportFragmentManager.beginTransaction().remove(myAccountFragment!!).commit()
            findViewById<FrameLayout>(R.id.fragment_parent).visibility = View.GONE
            myAccountFragment = null
            return
        }
        if (addProfileFragment != null) {
            supportFragmentManager.beginTransaction().remove(addProfileFragment!!).commit();
            findViewById<FrameLayout>(R.id.fragment_parent).visibility = View.GONE;
            addProfileFragment = null;
            return;
        }
        super.onBackPressed()
    }

    private fun getMultiProfileData(userName: String) {
        val multiProfileDao = MultiProfileDatabase.getDatabase(applicationContext).MultiProfileDao()
        var multiProfiles: MutableList<MultiProfile> = multiProfileDao.getMultiProfile(userId).toMutableList()
        if (multiProfiles != null && multiProfiles.isNotEmpty()) {
            multiProfiles.add(0, MultiProfile( userName, userName, 2, ""));
        } else {
            multiProfiles = ArrayList();
            multiProfiles.add(0, MultiProfile(userName, userName, 2, ""));
        }
        runOnUiThread{
            profileAdapter.updateProfileData(multiProfiles)
        }
    }

    private fun openProfileFragment() {
        val bundle: Bundle = Bundle();
        bundle.putParcelable("profileListenerObj", profileListener);
        findViewById<FrameLayout>(R.id.fragment_parent).visibility = View.VISIBLE;
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        addProfileFragment = AddProfileFragment();
        addProfileFragment?.arguments = bundle;
        transaction.replace(R.id.fragment_parent, addProfileFragment!!);
        transaction.commit();
    }

    interface ProfileListener : Parcelable {
        fun onCreateProfileClicked()
        fun onNewProfileCreated(profileName: String, profileType: Int, ageGroup: String)
    }

}
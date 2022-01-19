package com.example.kotlintv.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import com.example.kotlintv.DemoConstant
import com.example.kotlintv.R
import com.example.kotlintv.activity.AccountDetailsActivity
import com.example.kotlintv.database.UserProfileDB
import com.example.kotlintv.database.UserProfileDao
import com.example.kotlintv.database.UserTable
import kotlinx.android.synthetic.main.fragment_my_account.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyAccountFragment : Fragment() {

    private lateinit var nameET: EditText
    private lateinit var mailET: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var submitBtn: Button
    private lateinit var progressBarLayout: RelativeLayout
    private lateinit var spinnerAdapter: ArrayAdapter<CharSequence>

    private lateinit var userId: String
    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var userGender: String
    private var isUserDetailsAvailable: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_account, container, false)
        nameET = view.findViewById(R.id.edt_profile_name)
        mailET = view.findViewById(R.id.edt_email)
        genderSpinner = view.findViewById(R.id.edt_gender)
        submitBtn = view.findViewById(R.id.submit_btn)
        progressBarLayout = view.findViewById(R.id.progressBar_layout)
        progressBarLayout.visibility = View.GONE
        spinnerAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_list,
            android.R.layout.simple_spinner_item
        ).also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                genderSpinner.adapter = it
                genderSpinner.onItemSelectedListener = OnSpinnerSelectedListeners()
            }
        submitBtn.setOnClickListener {
            progressBarLayout.visibility = View.VISIBLE
            getEnteredValues()
        }

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        userId = sharedPreferences.getString(DemoConstant.SIGNED_IN_USER_ID, "").toString()

        getUserDetailsFromDB()

        return view
    }

    private fun getUserDetailsFromDB() {
        val userDetail: LiveData<UserTable>? = UserProfileDB.getUserDatabase(requireContext())
            ?.getUserProfileDao()?.getUserDetail(userId)
        userDetail?.observe(requireActivity(), {
            if (it != null){
                isUserDetailsAvailable = true
                if (it.user_name.isNotEmpty()){
                    nameET.setText(it.user_name)
                }
                if (it.user_email.isNotEmpty()){
                    mailET.setText(it.user_email)
                }
                if (it.user_gender.isNotEmpty()){
                    genderSpinner.setSelection(spinnerAdapter.getPosition(it.user_gender))
                }
            }
        })

    }

    private fun getEnteredValues() {
        if (nameET.text.isEmpty() || mailET.text.isEmpty()) {
            Toast.makeText(context, "Please fill all the field", Toast.LENGTH_LONG).show()
        } else {
            if (!mailET.text.contains("@")) {
                Toast.makeText(context, "Invalid Email Format", Toast.LENGTH_LONG).show()
            } else {
                userName = nameET.text.toString()
                userEmail = mailET.text.toString()
                userGender = genderSpinner.selectedItem.toString()
                setUserDetails()
                Handler(Looper.getMainLooper()).postDelayed({
                    progressBarLayout.visibility = View.GONE
                    Toast.makeText(context, "User Detail Updated!!!", Toast.LENGTH_LONG).show()
                }, 2000)
            }
        }
    }

    private fun setUserDetails(){
        val userDao : UserProfileDao? = UserProfileDB.getUserDatabase(requireContext())?.getUserProfileDao()
        GlobalScope.launch {
            if (isUserDetailsAvailable)
                userDao?.updateUserDetail(userId, userName, userEmail, userGender)
            else
                userDao?.insertUser(UserTable(0, userId, userName, userEmail, userGender))
        }
    }

    inner class OnSpinnerSelectedListeners : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            Log.d("OnSpinnerSelected", "onItemSelected: ${genderSpinner.selectedItem}")
            userGender = genderSpinner.selectedItem.toString()
            genderSpinner.setSelection(position)
            genderSpinner.isSelected = true
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            Log.d("OnSpinnerSelected", "onNothingSelected: ")
        }
    }


}
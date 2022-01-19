package com.example.kotlintv.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.kotlintv.R
import com.example.kotlintv.activity.AccountDetailsActivity
import android.widget.Toast

import android.widget.RadioButton

import android.widget.RadioGroup
import androidx.core.view.isVisible


class AddProfileFragment : Fragment() {

    private lateinit var profileName: EditText;

    private lateinit var ageGroupText: TextView;

    private lateinit var addProfileBtn: Button;

    private var profileArray: ArrayList<String> = ArrayList();

    private lateinit var kidsSwitch: Switch

    private lateinit var ageRadioGroup: RadioGroup

    private lateinit var radioButton: RadioButton

    private  var ageGroup : String = "";

    private  var profileType : Int = 2;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_add_profile, container, false)
        profileName = view.findViewById(R.id.name_edit_text);
        addProfileBtn = view.findViewById(R.id.profile_button);
        kidsSwitch = view.findViewById(R.id.contact_type_switch);
        ageRadioGroup = view.findViewById(R.id.radioGroup2);
        ageGroupText = view.findViewById(R.id.age_group_text);
        resetAddProfileButton(false);
        val bundle: Bundle? = arguments;
        val profileListener: AccountDetailsActivity.ProfileListener? =
            bundle?.getParcelable<AccountDetailsActivity.ProfileListener>("profileListenerObj")
        addProfileBtn.setOnClickListener(View.OnClickListener {
            if (!profileName.text.toString().trim().isNullOrEmpty()) {
                activity?.currentFocus?.let { view ->
                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }
                if (profileType == 1) {
                    setAgeGroup(view);
                }
                profileListener?.onNewProfileCreated(
                    profileName.text.toString(),
                    profileType,
                    ageGroup
                );
            } else {
                Toast.makeText(
                    activity,
                    "Profile name can't be empty", Toast.LENGTH_SHORT
                ).show()
            }
        })
        kidsSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // do something, the isChecked will be
            // true if the switch is in the On position
            if (isChecked) {
                profileType = 1
                ageRadioGroup.visibility = View.VISIBLE
                ageGroupText.isVisible = true;
            } else {
                profileType = 2
                ageRadioGroup.visibility = View.GONE
                ageGroupText.isVisible = false;
            }
        });

        ageRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            setAgeGroup(view);
        });

        profileName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s : Editable) {}

            override fun beforeTextChanged( s : CharSequence, start : Int ,
                                            count : Int, after : Int) {
            }

            override fun onTextChanged(s : CharSequence, start : Int,
                                       before : Int, count : Int) {
                if(s.isNotEmpty() && profileName.text.trim().isNotEmpty()) {
                    resetAddProfileButton(true);
                } else {
                    resetAddProfileButton(false);
                }
            }
        });

        return view;
    }

    private fun resetAddProfileButton(status: Boolean) {
        if (status) {
            addProfileBtn.setBackgroundResource(R.drawable.activate_offer_bn_white_bg);
        } else {
            addProfileBtn.setBackgroundResource(R.drawable.activate_offer_btn_bg);
        }
    }

    private fun setAgeGroup(view: View) {
        val selectedId: Int = ageRadioGroup.checkedRadioButtonId
        radioButton = view.findViewById(selectedId) as RadioButton
        ageGroup = radioButton.text.trim().toString();
    }

}
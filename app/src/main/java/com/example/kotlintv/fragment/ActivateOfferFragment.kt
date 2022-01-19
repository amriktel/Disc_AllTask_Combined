package com.example.kotlintv.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kotlintv.R
import com.example.kotlintv.activity.MainActivity
import com.example.kotlintv.activity.OfferActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily

class ActivateOfferFragment : Fragment(), View.OnClickListener {
    private val offerCode: HashMap<String, String> = HashMap<String, String>();
    private lateinit var offerBgImageView: ShapeableImageView;
    private lateinit var activateOfferBtn: Button;
    private lateinit var offerCodeEditTxt: EditText;
    private lateinit var resetLinear: LinearLayout;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.activate_offer, container, false)
        offerBgImageView = view.findViewById(R.id.image);
        activateOfferBtn = view.findViewById(R.id.activate_offer_bn);
        offerCodeEditTxt = view.findViewById(R.id.offer_code_edit_txt)
        resetLinear = view.findViewById(R.id.reset_layout);
        activateOfferBtn.setOnClickListener(this);
        resetLinear.setOnClickListener(this);
        offerCodeEditTxt.requestFocus()
        val curveRadius = 30F

        offerBgImageView.setShapeAppearanceModel(
            offerBgImageView.getShapeAppearanceModel()
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED, curveRadius)
                .setTopLeftCorner(CornerFamily.ROUNDED, curveRadius)
                .build()
        );
        offerCodeEditTxt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s : Editable) {}

            override fun beforeTextChanged( s : CharSequence, start : Int ,
                 count : Int, after : Int) {
            }

            override fun onTextChanged(s : CharSequence, start : Int,
                before : Int, count : Int) {
                if(s.isNotEmpty() && offerCodeEditTxt.text.trim().isNotEmpty()) {
                    resetActivationButton(true);
                } else {
                    resetActivationButton(false);
                }
            }
        });
        loadOfferCode();
        resetActivationButton(false);
        return view
    }

    private fun loadOfferCode() {
        offerCode["1"] = "123456"
        offerCode["2"] = "113456"
        offerCode["3"] = "111456"
        offerCode["4"] = "111156"
        offerCode["5"] = "111116"
        offerCode["6"] = "111111"
        offerCode["7"] = "223456"
        offerCode["8"] = "222456"
        offerCode["9"] = "222256"
        offerCode["10"] = "222226"
    }

    private fun checkActivationCode() {
        if (offerCode.containsValue(offerCodeEditTxt.text.trim().toString())) {
            //Toast.makeText(this.context, "Activation Success", Toast.LENGTH_SHORT).show();
            context?.startActivity(Intent(context,OfferActivity::class.java))
        } else {
            Toast.makeText(this.context, "Invalid Code", Toast.LENGTH_SHORT).show();
        }

    }

    private fun resetCode() {
        offerCodeEditTxt.setText("");
        resetActivationButton(false);
    }

    private fun resetActivationButton(status: Boolean) {
        if (status) {
            activateOfferBtn.setBackgroundResource(R.drawable.activate_offer_bn_white_bg);
            activateOfferBtn.isEnabled = true;
        } else {
            activateOfferBtn.setBackgroundResource(R.drawable.activate_offer_btn_bg);
            activateOfferBtn.isEnabled = false;
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.activate_offer_bn -> checkActivationCode();
            R.id.reset_layout -> resetCode();
        }
    }
}
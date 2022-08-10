package com.neppplus.colosseum_okhttp_20220810

import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_okhttp_20220810.databinding.ActivitySignUpBinding
import com.neppplus.colosseum_okhttp_20220810.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.emailDupBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()

            checkDuplicate("EMAIL", inputEmail, binding.emailDupTxt)
        }

        binding.nickDupBtn.setOnClickListener {
            val inputNick = binding.nickEdt.text.toString()

            checkDuplicate("Nick_NAME", inputNick, binding.emailDupTxt)
        }

        binding.signUpBtn.setOnClickListener {  }
    }

    override fun setValues() {

    }

    fun checkDuplicate (type : String, value : String, textView : TextView) {
        ServerUtil.getRequestUserCheck(type, value, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val code = jsonObj.getInt("code")
                val message = jsonObj.getString("message")

                runOnUiThread {
                    textView.text = message
                }
            }
        })
    }
}
package com.neppplus.colosseum_okhttp_20220810

import android.os.Bundle
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

        }

        binding.nickDupBtn.setOnClickListener {
            val inputNick = binding.nickEdt.text.toString()

            ServerUtil.getRequestUserCheck(
                "NICK_NAME",
                inputNick,
                object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObj: JSONObject) {
                        val code = jsonObj.getInt("code")
                        val message = jsonObj.getString("message")

                        runOnUiThread {
                            binding.nickDupTxt.text = message
                        }
                    }
                }
            )
        }

        binding.signUpBtn.setOnClickListener {  }
    }

    override fun setValues() {

    }
}
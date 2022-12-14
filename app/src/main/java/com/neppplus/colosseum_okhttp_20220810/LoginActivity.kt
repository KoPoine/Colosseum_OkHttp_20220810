package com.neppplus.colosseum_okhttp_20220810

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_okhttp_20220810.databinding.ActivityLoginBinding
import com.neppplus.colosseum_okhttp_20220810.datas.UserData
import com.neppplus.colosseum_okhttp_20220810.utils.ContextUtil
import com.neppplus.colosseum_okhttp_20220810.utils.GlobalData
import com.neppplus.colosseum_okhttp_20220810.utils.ServerUtil
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {
        binding.loginBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            val inputPw = binding.passwordEdt.text.toString()

            ServerUtil.postRequestLogin(inputEmail, inputPw, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {

//                    UI 입장에서, 로그인 결과를 받아서 처리할 코드
//                    서버에 다녀오고 나서 실행 : OkHttp 라이브러리가 자동으로 백그라운드에서 돌도록 만들어둔 코드
                    val code = jsonObj.getInt("code")
                    val message = jsonObj.getString("message")

                    if (code == 200) {
//                        로그인 성공
                        val dataObj = jsonObj.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")
                        val token = dataObj.getString("token")

                        ContextUtil.setLoginToken(mContext, token)

                        ContextUtil.setAutoLogin(mContext, binding.autoLoginCb.isChecked)

                        GlobalData.loginUser = UserData.getUserDataFromJson(userObj)

                        runOnUiThread {
                            Toast.makeText(mContext, "${GlobalData.loginUser!!.nick}님 환영합니다.", Toast.LENGTH_SHORT).show()
                            val myIntent = Intent(mContext, MainActivity::class.java)
                            startActivity(myIntent)
                            finish()
                        }
                    }
                    else {
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

//            서버에 Request를 보내는 로직 (예시)
//            val url = "http://54.180.52.26/user"
//
//            val formData = FormBody.Builder()
//                .add("email", inputEmail)
//                .add("password", inputPw)
//                .build()
//
//            val request = Request.Builder()
//                .url(url)
//                .post(formData)
//                .build()
//
//            val client = OkHttpClient()
//
//            client.newCall(request).enqueue(object : Callback{
//                override fun onFailure(call: Call, e: IOException) {
//
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    val bodyString = response.body!!.string()
//                    val jsonObj = JSONObject(bodyString)
//                    val code = jsonObj.getInt("code")
//
//                    if (code == 200) {
////                        로그인 성공
//                    }
//                    else {
//                        runOnUiThread {
//                            Toast.makeText(mContext, "로그인에 실패", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            })

        }

        binding.signUpBtn.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        binding.autoLoginCb.isChecked = ContextUtil.getAutoLogin(mContext)
    }
}
package com.neppplus.colosseum_okhttp_20220810

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun setupEvents() {

    }

    override fun setValues() {


        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed(
            {
//            1. 실제로 유저가 자동로그인에 체크를 하였는가?
                val isAutoLoginOk = false

//            2. 유저의 자동로그인 정보는 확실한가(서버와 통신)?
                val isTokenOk = false

                if (isAutoLoginOk && isTokenOk) {
                    val myIntent = Intent(mContext, MainActivity::class.java)
                    startActivity(myIntent)
                } else {
                    val myIntent = Intent(mContext, LoginActivity::class.java)
                    startActivity(myIntent)
                }
                finish()

            }, 2500
        )
    }
}
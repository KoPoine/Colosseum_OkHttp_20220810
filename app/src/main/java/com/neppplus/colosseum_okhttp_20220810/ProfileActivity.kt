package com.neppplus.colosseum_okhttp_20220810

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_okhttp_20220810.databinding.ActivityProfileBinding
import com.neppplus.colosseum_okhttp_20220810.utils.GlobalData

class ProfileActivity : BaseActivity() {

    lateinit var binding : ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
//        닉네임 변경이벤트
        binding.changeNickBtn.setOnClickListener {

        }
    }

    override fun setValues() {
//        닉네임을 표시하는 작업
        binding.nickTxt.text = GlobalData.loginUser!!.nick
    }
}
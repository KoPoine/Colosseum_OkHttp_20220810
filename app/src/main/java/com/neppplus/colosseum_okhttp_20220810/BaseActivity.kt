package com.neppplus.colosseum_okhttp_20220810

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setCustomActionBar()
    }

    abstract fun setupEvents()
    abstract fun setValues()


    fun setCustomActionBar () {
//        기존 액션바를 담아줄 변수
        val defaultActionBar = supportActionBar!!

//        기존 액션바를 커스텀 모드로 변경 > 우리가 만든 xml로 적용
//        ActionBar는 Androidx에서 주는 걸로 자동 완성
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defaultActionBar.setCustomView(R.layout.custom_action_bar)

//        Toolbar를 찾아내서 > 양 옆의 여백 제거 -> 모든 영역이 커스텀 뷰
//        Toolbar 찾는다 > 우리의 액션바의 부모 > Androidx에서 제공하는 Toolbar 형 변환
        val myToolbar = defaultActionBar.customView.parent as Toolbar
        myToolbar.setContentInsetsAbsolute(0,0)

        val backIcon = defaultActionBar.customView.findViewById<ImageView>(R.id.backIcon)
        val titleTxt = defaultActionBar.customView.findViewById<TextView>(R.id.titleTxt)
        val profileIcon = defaultActionBar.customView.findViewById<ImageView>(R.id.profileIcon)

        profileIcon.visibility = View.VISIBLE
    }
}
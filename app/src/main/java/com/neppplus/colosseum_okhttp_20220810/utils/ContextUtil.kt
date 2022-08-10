package com.neppplus.colosseum_okhttp_20220810.utils

import android.content.Context

class ContextUtil {

    companion object {
        val prefName = "Colosseum_Pref"

        val LOGIN_TOKEN = "LOGIN_TOKEN"
        val AUTO_LOGIN = "AUTO_LOGIN"

        fun setLoginToken (context : Context, token : String) {
//            메모장을 만들어준다.
//            (String => 메모장의 이름, Int => 메모장의 모드(단독사용 / 다른앱에서 활용 가능한지?))
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
//            메모장에 내용을 기록(set)
            pref.edit().putString(LOGIN_TOKEN, token).apply()
        }

        fun getLoginToken (context: Context) : String {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(LOGIN_TOKEN, "")!!
        }

        fun setAutoLogin (context: Context, isAuto : Boolean) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putBoolean(AUTO_LOGIN, isAuto).apply()
        }

        fun getAutoLogin (context: Context) : Boolean {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getBoolean(AUTO_LOGIN, false)
        }

//        SharedPreferences에 적힌 모든 변수 초기화(삭제)
        fun clear( context: Context ) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }
    }

}
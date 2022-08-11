package com.neppplus.colosseum_okhttp_20220810

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_okhttp_20220810.databinding.ActivityProfileBinding
import com.neppplus.colosseum_okhttp_20220810.datas.UserData
import com.neppplus.colosseum_okhttp_20220810.utils.ContextUtil
import com.neppplus.colosseum_okhttp_20220810.utils.GlobalData
import com.neppplus.colosseum_okhttp_20220810.utils.ServerUtil
import org.json.JSONObject

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

            val customView = LayoutInflater.from(mContext).inflate(R.layout.custom_alert_dialog, null)

            val inputNickEdt = customView.findViewById<EditText>(R.id.nickEdt)

            val alert = AlertDialog.Builder(mContext)
                .setView(customView)
                .setPositiveButton("저장", DialogInterface.OnClickListener { dialogInterface, i ->
//                    서버에 사용자가 작성한 nickname으로 변경 이벤트 처리
                    val inputNick = inputNickEdt.text.toString()

                    changeUserData(inputNick)
                })
                .setNegativeButton("취소", null)
                .show()


        }
    }

    override fun setValues() {
//        닉네임을 표시하는 작업
        binding.nickTxt.text = GlobalData.loginUser!!.nick

        backIcon.visibility = View.VISIBLE
    }

    fun changeUserData(nick : String) {
        val token = ContextUtil.getLoginToken(mContext)
        ServerUtil.patchRequestChangeProfile(token, nick, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val code = jsonObj.getInt("code")
                val message = jsonObj.getString("message")
                if (code == 200) {
                    val dataObj = jsonObj.getJSONObject("data")
                    val userObj = dataObj.getJSONObject("user")
                    GlobalData.loginUser = UserData.getUserDataFromJson(userObj)

                    runOnUiThread {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

                        binding.nickTxt.text = GlobalData.loginUser!!.nick
                    }
                }
                else {
                    runOnUiThread {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
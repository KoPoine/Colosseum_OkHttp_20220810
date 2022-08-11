package com.neppplus.colosseum_okhttp_20220810

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
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
                .setTitle("닉네임 변경")
                .setView(customView)
                .setPositiveButton("저장", DialogInterface.OnClickListener { dialogInterface, i ->
//                    서버에 사용자가 작성한 nickname으로 변경 이벤트 처리
                    val inputNick = inputNickEdt.text.toString()

                    changeUserData(null, null, inputNick)
                })
                .setNegativeButton("취소", null)
                .show()


        }

//        비밀번호 변경 EditText 입력시 현재 비밀번호 입력 Layout visibility 변경
        binding.inputPwEdt.addTextChangedListener {
            if (it.toString().isNotBlank()) {
                binding.changePwLayout.visibility = View.VISIBLE
            }
            else {
                binding.changePwLayout.visibility = View.GONE
            }
        }

//        비밀번호 변경 이벤트 처리
        binding.changePwBtn.setOnClickListener {
            val currentPw = binding.currentEdt.text.toString()
            val inputPw = binding.inputPwEdt.text.toString()

            changeUserData(currentPw, inputPw, null)
        }

//        회원탈퇴 이벤트 처리
        binding.deleteBtn.setOnClickListener {
            val customView = LayoutInflater.from(mContext).inflate(R.layout.custom_alert_dialog, null)

            val inputNickEdt = customView.findViewById<EditText>(R.id.nickEdt)

            inputNickEdt.hint = "'동의'라고 작성해주세요."

            val alert = AlertDialog.Builder(mContext)
                .setView(customView)
                .setTitle("회원 탈퇴")
                .setMessage("탈퇴하시려면 '동의'라면 작성해주세요.")
                .setPositiveButton("저장", DialogInterface.OnClickListener { dialogInterface, i ->

                    val text = inputNickEdt.text.toString()
                    val token = ContextUtil.getLoginToken(mContext)

                    ServerUtil.deleteRequestDeleteUser(token, text, object : ServerUtil.JsonResponseHandler{
                        override fun onResponse(jsonObj: JSONObject) {
                            val message = jsonObj.getString("message")

//                            탈퇴 성공 => GlobalData, ContextUtil 모두 정리 필요
                            GlobalData.loginUser = null
                            ContextUtil.clear(mContext)

                            runOnUiThread {
//                                탈퇴 성공 후 > LoginActivity로 이동 (Activity Stack을 모두 제거한)
                                val myIntent = Intent(mContext, LoginActivity::class.java)
                                startActivity(myIntent)
                                finishAffinity()   // 앱의 Stack으로 쌓인 모든 액티비티를 종료

                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })


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

    fun changeUserData(currentPw : String?, newPw : String?, nick : String?) {
        val token = ContextUtil.getLoginToken(mContext)
        ServerUtil.patchRequestChangeProfile(
            token, currentPw, newPw, nick, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val code = jsonObj.getInt("code")
                val message = jsonObj.getString("message")
                if (code == 200) {
                    val dataObj = jsonObj.getJSONObject("data")
                    val userObj = dataObj.getJSONObject("user")
                    val token = dataObj.getString("token")
                    GlobalData.loginUser = UserData.getUserDataFromJson(userObj)

                    ContextUtil.setLoginToken(mContext, token)

                    runOnUiThread {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

                        binding.changePwLayout.visibility = View.GONE

                        binding.currentEdt.setText("")

                        binding.inputPwEdt.setText("")

                        binding.nickTxt.text = GlobalData.loginUser!!.nick
                    }
                }
                else {
                    runOnUiThread {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

//                        [도전과제] 비밀번호 변경시 message를 EditText 아래쪽에 있는 textView에 배치
                    }
                }
            }
        })
    }
}
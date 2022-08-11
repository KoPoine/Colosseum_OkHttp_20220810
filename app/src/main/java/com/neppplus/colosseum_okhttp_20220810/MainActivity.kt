package com.neppplus.colosseum_okhttp_20220810

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.colosseum_okhttp_20220810.adapters.TopicRecyclerAdapter
import com.neppplus.colosseum_okhttp_20220810.databinding.ActivityMainBinding
import com.neppplus.colosseum_okhttp_20220810.datas.TopicData
import com.neppplus.colosseum_okhttp_20220810.utils.ContextUtil
import com.neppplus.colosseum_okhttp_20220810.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    val mTopicList = ArrayList<TopicData>()
    lateinit var mTopicAdapter : TopicRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
//        로그아웃 클릭 이벤트
        binding.logoutBtn.setOnClickListener {
            ContextUtil.clear(mContext)

//            ContextUtil.setLoginToken(mContext, "")

            val myIntent = Intent(mContext, LoginActivity::class.java)
            startActivity(myIntent)
            finish()
        }

//        내 정보 화면으로 이동 (커스텀 액션바의 icon 클릭이벤트 활용)
        profileIcon.setOnClickListener {
            val myIntent = Intent()
        }
    }

    override fun setValues() {
        profileIcon.visibility = View.VISIBLE

        getTopicListFromServer()

        mTopicAdapter = TopicRecyclerAdapter(mContext, mTopicList)
        binding.topicRecyclerView.adapter = mTopicAdapter
        binding.topicRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getTopicListFromServer() {
        val token = ContextUtil.getLoginToken(mContext)
        ServerUtil.getRequestMainInfo(token, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val dataObj = jsonObj.getJSONObject("data")
                val topicsArr = dataObj.getJSONArray("topics")

//                Log.d("topics", topicsArr.toString())

//                [  ] => {  }, {  }, ... 순서에 맞춰서 설정된 JsonObj를 뽑아오자(배열에서)
                for (i in 0 until topicsArr.length()) {
//                    JSON 파싱의 {  } => JSONArray 안에서 JSONObject를 하나씩 get함수 사용
                    val topicObj = topicsArr.getJSONObject(i)

                    Log.d("받아낸 주제", topicObj.toString())

//                    val topicData = TopicData()
//
//                    topicData.id = topicObj.getInt("id")
//                    topicData.title = topicObj.getString("title")
//                    topicData.imageUrl = topicObj.getString("img_url")
//                    topicData.replyCount = topicObj.getInt("reply_count")

//                    TopicData 변수를 생성 => companion Obj 활용 > topicData를 뽑아오는 로직
                    val topicData = TopicData.getTopicDataFromJson(topicObj)

//                    뽑아낸 topicData를 ArrayList에 추가
                    mTopicList.add(topicData)

                    runOnUiThread {
//                        mTopicList에 한개의 topicData를 넣는것보다 (서버통신)
//                        mTopicAdapter를 먼저 객체화 한 경우를 대비해서
                        mTopicAdapter.notifyDataSetChanged()
                    }

                }
            }
        })
    }

}
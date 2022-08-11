package com.neppplus.colosseum_okhttp_20220810

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.colosseum_okhttp_20220810.databinding.ActivityDetailTopicBinding
import com.neppplus.colosseum_okhttp_20220810.datas.TopicData

class DetailTopicActivity : BaseActivity() {

    lateinit var binding : ActivityDetailTopicBinding

    lateinit var topicData : TopicData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_topic)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        topicData = intent.getSerializableExtra("topicData") as TopicData

        Glide.with(mContext).load(topicData.imageUrl).into(binding.backgroundImg)

        binding.side1Txt.text = topicData.sideList[0].title
        binding.vote1CountTxt.text = "${topicData.sideList[0].voteCount}표"

        binding.side2Txt.text = topicData.sideList[1].title
        binding.vote2CountTxt.text = "${topicData.sideList[1].voteCount}표"
    }


}
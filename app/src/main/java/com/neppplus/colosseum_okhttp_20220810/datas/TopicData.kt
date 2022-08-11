package com.neppplus.colosseum_okhttp_20220810.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData : Serializable {

//    생성자 : 기본 생성자 유지
//    멤버변수만 따로 추가 -> JSON 파싱해서 변수에 채워넣자
//    멤버변수 : 서버의 데이터를 보고 -> 담아두는 용도의 변수들로 만들자.

    var id = 0  // Int가 들어올 자리라는 표시
    var title = ""  // String이 들어올 자리
    var imageUrl = ""
    var replyCount = 0

//    토론 주제(TopicData)의 하위 항목 -> 선택 진영(SideData) 목록(Array)
     val sideList = ArrayList<SideData>()


    companion object {
//    주제 정보를 담고 있는 JSONObject가 들어오면 > TopicData로 변환해주는 함수
//    다른 화면들에서는 이 함수를 끌어다 사용만 하도록(Companion Object)

        fun getTopicDataFromJson(jsonObj : JSONObject) : TopicData {
            val topicData = TopicData()

            topicData.id = jsonObj.getInt("id")
            topicData.title = jsonObj.getString("title")
            topicData.imageUrl = jsonObj.getString("img_url")
            topicData.replyCount = jsonObj.getInt("reply_count")

            val sideArr = jsonObj.getJSONArray("sides")
            for (i in 0 until sideArr.length()) {
                val sideObj = sideArr.getJSONObject(i)

                val sideData = SideData.getSideDataFromJson(sideObj)

                topicData.sideList.add(sideData)
            }

            return topicData
        }
    }

}
package com.neppplus.colosseum_okhttp_20220810.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.colosseum_okhttp_20220810.R
import com.neppplus.colosseum_okhttp_20220810.datas.ReplyData
import org.w3c.dom.Text

class ReplyRecyclerAdapter(
    val mContext : Context, val mList: List<ReplyData>
) : RecyclerView.Adapter<ReplyRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(view:View) : RecyclerView.ViewHolder(view) {

        fun bind (item : ReplyData) {

            val nickTxt = itemView.findViewById<TextView>(R.id.nickTxt)
            val sideTxt = itemView.findViewById<TextView>(R.id.sideTxt)
            val timeTxt = itemView.findViewById<TextView>(R.id.timeTxt)
            val contentTxt = itemView.findViewById<TextView>(R.id.contentTxt)
            val replyCountTxt = itemView.findViewById<TextView>(R.id.replyCountTxt)
            val likeCountTxt = itemView.findViewById<TextView>(R.id.likeCountTxt)
            val dislikeCountTxt = itemView.findViewById<TextView>(R.id.dislikeCountTxt)

            nickTxt.text = item.user.nick
            sideTxt.text = "(${item.selectedSide.title})"
            timeTxt.text = item.createdAt   // 시간 양식으로 포맷
            contentTxt.text = item.content
            replyCountTxt.text = "답글 : ${item.replyCount}표"
            likeCountTxt.text = "좋아요 : ${item.likeCount}표"
            dislikeCountTxt.text = "싫어요 : ${item.disLikeCount}표"

//            [도전과제] 좋아요, 싫어요 버튼 클릭 이벤트 처리
//            1. drawable 생성 > red_border_box, blue_border_box

//            2. myLike / myDisLike의 속성(Boolean값)에 따라 (좋아요 > red_border_box, 싫어요 > blue_border_box)

//            3. 클릭 이벤트를 통해서 그 댓글의 좋아요 싫어요에 대한 서버 응답을 작성

//            hint. view의 backgroundResource변경 > view변수.setBackgroundResource(R.drawable.~~)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.reply_list_item, parent, false)
        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}
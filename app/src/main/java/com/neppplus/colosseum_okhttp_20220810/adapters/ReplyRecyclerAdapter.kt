package com.neppplus.colosseum_okhttp_20220810.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.colosseum_okhttp_20220810.R
import com.neppplus.colosseum_okhttp_20220810.datas.ReplyData

class ReplyRecyclerAdapter(
    val mContext : Context, val mList: List<ReplyData>
) : RecyclerView.Adapter<ReplyRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(view:View) : RecyclerView.ViewHolder(view) {

        fun bind (item : ReplyData) {

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
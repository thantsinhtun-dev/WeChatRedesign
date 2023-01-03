package com.stone.wechat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.R
import com.stone.wechat.data.vos.ChatHistoryVO
import com.stone.wechat.viewholder.ChatHistoryViewHolder

class ChatHistoryAdapter: RecyclerView.Adapter<ChatHistoryViewHolder>() {
    private var mData :List<ChatHistoryVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chats,parent,false)
        return ChatHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatHistoryViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.size
//        return 10
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data: List<ChatHistoryVO>){
        mData = data
        notifyDataSetChanged()
    }
}
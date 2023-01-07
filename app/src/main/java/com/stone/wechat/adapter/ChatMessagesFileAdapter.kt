package com.stone.wechat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.R
import com.stone.wechat.viewholder.ChatMessagesFilesViewHolder

class ChatMessagesFileAdapter: RecyclerView.Adapter<ChatMessagesFilesViewHolder>() {
    private var mData :List<String> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessagesFilesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_message_image,parent,false)
        return ChatMessagesFilesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatMessagesFilesViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data:List<String>){
        mData = data
        notifyDataSetChanged()
    }
}
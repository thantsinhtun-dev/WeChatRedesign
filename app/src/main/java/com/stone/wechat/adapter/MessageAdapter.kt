package com.stone.wechat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.R
import com.stone.wechat.data.vos.MessagesVO
import com.stone.wechat.viewholder.ViewHolderMessage

class MessageAdapter: RecyclerView.Adapter<ViewHolderMessage>() {
    private var mData :List<MessagesVO> = listOf()
    private var currentUserId:String = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMessage {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_messages,parent,false)
        return ViewHolderMessage(view)
    }

    override fun onBindViewHolder(holder: ViewHolderMessage, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position],currentUserId)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
//        return 10
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data:List<MessagesVO>,currentUserId:String){
        mData = data
        this.currentUserId = currentUserId
        notifyDataSetChanged()
    }
}
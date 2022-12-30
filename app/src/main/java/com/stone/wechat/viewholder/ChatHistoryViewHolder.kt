package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stone.wechat.data.vos.ChatHistoryVO
import kotlinx.android.synthetic.main.view_holder_chats.view.*

class ChatHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(mData:ChatHistoryVO){
        itemView.lblUserName.text = mData.chatUserName
        Glide.with(itemView.context)
            .load(mData.chatIcon)
            .into(itemView.imgChatIcon)
    }
}
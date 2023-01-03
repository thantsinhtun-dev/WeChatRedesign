package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stone.wechat.data.vos.ChatHistoryVO
import kotlinx.android.synthetic.main.view_holder_chats.view.*

class ChatHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(mData: ChatHistoryVO) {

//        mData.messages?.sortedByDescending { it.timeStamp }?.firstOrNull().apply {
        itemView.lblUserName.text = mData.userId
        itemView.lblMessageSender.text = mData.messages?.senderName.plus(" : ")
        itemView.lblLastMessage.text = mData.messages?.message
//
//            Glide.with(itemView.context)
//                .load(mData.userId)
//                .into(itemView.imgChatIcon)
//        }

    }
}
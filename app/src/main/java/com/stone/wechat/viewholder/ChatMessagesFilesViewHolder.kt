package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_holder_chat_message_image.view.*

class ChatMessagesFilesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(mData:String){
        Glide.with(itemView.context)
            .load(mData)
            .into(itemView.imgAddMomentContent)
    }
}
package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.data.vos.MessagesVO
import com.stone.wechat.utils.formatDate
import kotlinx.android.synthetic.main.view_holder_messages.view.*

class ViewHolderMessage(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(mData: MessagesVO, currentUserId: String){
        itemView.rlChatOfContactUser.visibility = View.GONE
        itemView.rlChatOfSender.visibility = View.GONE

        if (mData.senderId == currentUserId){
            itemView.rlChatOfSender.visibility = View.VISIBLE
            itemView.lblSenderMessage.text = mData.message
            itemView.lblSenderMessageDate.text = mData.timeStamp?.formatDate()
        }else{
            itemView.rlChatOfContactUser.visibility = View.VISIBLE
            itemView.lblContactMessage.text = mData.message
            itemView.lblContactMessageDate.text = mData.timeStamp?.formatDate()
        }
//        itemView.lblContactMessage.text = mData.message
    }
}
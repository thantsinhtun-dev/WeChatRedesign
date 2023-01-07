package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stone.wechat.R
import com.stone.wechat.adapter.ChatMessagesFileAdapter
import com.stone.wechat.data.vos.MessagesVO
import com.stone.wechat.utils.formatDate
import kotlinx.android.synthetic.main.view_holder_messages.view.*

class ViewHolderMessage(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mMessagesFileAdapter = ChatMessagesFileAdapter()
    init {
        itemView.rvSenderChatMessageFile.adapter = mMessagesFileAdapter
        itemView.rvContactChatMessageFile.adapter = mMessagesFileAdapter

    }

    fun bindData(mData: MessagesVO, currentUserId: String){
        itemView.rlChatOfContactUser.visibility = View.GONE
        itemView.rlChatOfSender.visibility = View.GONE

        if (mData.senderId == currentUserId){
            itemView.rlChatOfSender.visibility = View.VISIBLE
            if (mData.message?.isNotEmpty() == true) {
                itemView.llSender.visibility = View.VISIBLE
                itemView.lblSenderMessage.text = mData.message
                itemView.lblSenderMessageDate.text = mData.timeStamp?.formatDate()
                mMessagesFileAdapter.setNewData(arrayListOf())

            }else {
                itemView.llSender.visibility = View.GONE
                mData.file?.let { files ->
                    if (files.isNotEmpty()) {
                        mMessagesFileAdapter.setNewData(files)
                    }
                }
            }

        }else{
            itemView.rlChatOfContactUser.visibility = View.VISIBLE
            Glide.with(itemView.context)
                .load(mData.senderProfile)
                .placeholder(R.drawable.ic_avator)
                .into(itemView.imgContactUserProfile)
            if (mData.message?.isNotEmpty() == true) {
                itemView.llContact.visibility = View.VISIBLE
                itemView.lblContactMessage.text = mData.message
                itemView.lblContactMessageDate.text = mData.timeStamp?.formatDate()
                mMessagesFileAdapter.setNewData(arrayListOf())

            }else {
                itemView.llContact.visibility = View.GONE
            mData.file?.let { files ->
                if (files.isNotEmpty()){
                    mMessagesFileAdapter.setNewData(files)
                }
            }
            }
        }
//        itemView.lblContactMessage.text = mData.message
    }
}
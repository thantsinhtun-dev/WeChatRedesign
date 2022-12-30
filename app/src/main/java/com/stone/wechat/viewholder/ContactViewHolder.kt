package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stone.wechat.data.vos.ContactVO
import kotlinx.android.synthetic.main.view_holder_contact.view.*

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(mData:ContactVO){
        itemView.lblContactsName.text = mData.contactName
        Glide.with(itemView.context)
            .load(mData.contactImage)
            .into(itemView.imgContact)
    }
}
package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stone.wechat.R
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.delegates.SelectedContactDelegate
import kotlinx.android.synthetic.main.view_holder_selected_contacts.view.*

class SelectedContactViewHolder(itemView: View, mDelegate: SelectedContactDelegate) : RecyclerView.ViewHolder(itemView) {
    private var mContactVO:ContactVO? = null
    init {
        itemView.imgRemoveContact.setOnClickListener {
            mContactVO?.let {
                mDelegate.onTapRemoveContact(it)
            }
        }
    }
    fun bindData(mData:ContactVO){
        mContactVO = mData
        itemView.lblSelectedContactName.text = mData.contactName
        Glide.with(itemView.context)
            .load(mData.contactImage)
            .placeholder(R.drawable.ic_avator)
            .into(itemView.imgSelectedContact)
    }
}
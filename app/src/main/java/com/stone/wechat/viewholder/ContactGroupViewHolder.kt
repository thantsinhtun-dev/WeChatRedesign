package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.adapter.ContactAdapter
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.delegates.ContactDelegate
import kotlinx.android.synthetic.main.view_holder_contacts_group.view.*

class ContactGroupViewHolder(itemView: View,private val isEditMode: Boolean) : RecyclerView.ViewHolder(itemView) {



    fun bindData(
        mData: List<ContactVO>,
        char: String,
        mDelegate: ContactDelegate,
    ) {
        val mContactAdapter = ContactAdapter(mDelegate,isEditMode)
        itemView.rvContact.adapter = mContactAdapter

        itemView.lblContactsGroupName.text = char
        mContactAdapter.setNewData(mData)


    }
}
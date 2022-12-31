package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.adapter.ContactAdapter
import com.stone.wechat.data.vos.ContactVO
import kotlinx.android.synthetic.main.view_holder_contacts_group.view.*

class ContactGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private  var mContactAdapter: ContactAdapter = ContactAdapter()

    init {
        itemView.rvContact.adapter = mContactAdapter
    }
    fun bindData(mData: List<ContactVO>, char: String){
            itemView.lblContactsGroupName.text = char
            mContactAdapter.setNewData(mData,isEdit = false)
//        }


    }
}
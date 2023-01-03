package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stone.wechat.R
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.delegates.ContactDelegate
import kotlinx.android.synthetic.main.view_holder_contact.view.*

class ContactViewHolder(itemView: View, mDelegate: ContactDelegate, isEdit: Boolean) : RecyclerView.ViewHolder(itemView) {

    private var mContactVO:ContactVO? = null
    init {
        itemView.setOnClickListener {
            mContactVO?.let { vo ->
                mDelegate.onTapContact(vo)
            }

        }

        itemView.cbContact.setOnClickListener { isChecked ->
            mContactVO?.let { vo ->
                vo.isSelected = itemView.cbContact.isChecked
                mDelegate.onTapCheckBox(vo)
            }
        }


        if (!isEdit){
            itemView.cbContact.visibility = View.GONE

        }
    }
    fun bindData(mData: ContactVO ){
        this.mContactVO = mData

        itemView.cbContact.isChecked = mData.isSelected

        itemView.lblContactsName.text = mData.contactName
        Glide.with(itemView.context)
            .load(mData.contactImage)
            .placeholder(R.drawable.ic_avator)
            .into(itemView.imgContact)




    }
}
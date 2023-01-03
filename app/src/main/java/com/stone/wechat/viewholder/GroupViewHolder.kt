package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stone.wechat.R
import com.stone.wechat.data.vos.GroupVO
import com.stone.wechat.delegates.ContactGroupDelegate
import kotlinx.android.synthetic.main.view_holder_group.view.*

class GroupViewHolder(itemView: View, mDelegate: ContactGroupDelegate) :
    RecyclerView.ViewHolder(itemView) {
    private var mGroupVO: GroupVO? = null

    init {

        itemView.rlHeader.setOnClickListener {
            mGroupVO?.let { vo ->

                mDelegate.onTapGroup(vo)
            }
        }

    }

    fun bindData(mData: GroupVO) {
        mGroupVO = mData
        itemView.lblGroupName.text = mData.groupName
        Glide.with(itemView.context)
            .load(mData.groupPhoto)
            .placeholder(R.drawable.ic_default_group)
            .into(itemView.imgGroupPhoto)
    }
}
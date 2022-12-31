package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stone.wechat.data.vos.GroupVO
import kotlinx.android.synthetic.main.view_holder_group.view.*

class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(mData:GroupVO){
        itemView.lblGroupName.text = mData.groupName
        Glide.with(itemView.context)
            .load(mData.groupIcon)
            .into(itemView.imgGroupPhoto)
    }
}
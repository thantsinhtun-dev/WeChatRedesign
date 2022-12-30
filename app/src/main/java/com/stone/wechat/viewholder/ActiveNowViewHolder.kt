package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stone.wechat.data.vos.UserVO
import kotlinx.android.synthetic.main.view_holder_active_user.view.*

class ActiveNowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(mData:UserVO){

        itemView.lblUserName.text = mData.name
        Glide.with(itemView.context)
            .load(mData.profile)
            .into(itemView.imgUser)

    }
}
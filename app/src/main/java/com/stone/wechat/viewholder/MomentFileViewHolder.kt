package com.stone.wechat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stone.wechat.R
import com.stone.wechat.data.vos.MomentFileVO
import kotlinx.android.synthetic.main.view_holder_add_moment_content.view.*

class MomentFileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(mData:MomentFileVO){

        itemView.imgAddMomentContent.setImageBitmap(mData.content)
//        Glide.with(itemView)
//            .asBitmap()
//            .load(mData.content)
//            .placeholder(R.drawable.background_progress)
//            .into(itemView.imgAddMomentContent)
    }
}
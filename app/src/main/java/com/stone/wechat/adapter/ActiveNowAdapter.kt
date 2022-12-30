package com.stone.wechat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.R
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.viewholder.ActiveNowViewHolder

class ActiveNowAdapter : RecyclerView.Adapter<ActiveNowViewHolder>() {
    private var mData: List<UserVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveNowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_active_user, parent, false)
        return ActiveNowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActiveNowViewHolder, position: Int) {
        if (mData.isNotEmpty()) {
            holder.bindData(mData[position])
        }
    }

    override fun getItemCount(): Int {
//        return mData.size
        return 10
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data: List<UserVO>) {
        mData = data
        notifyDataSetChanged()
    }
}
package com.stone.wechat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.R
import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.viewholder.MomentViewHolder

class MomentAdapter: RecyclerView.Adapter<MomentViewHolder>() {
    private var mData :List<MomentVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_moments,parent,false)
        return MomentViewHolder(view)
    }

    override fun onBindViewHolder(holder: MomentViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data:List<MomentVO>){
        mData = data
        notifyDataSetChanged()
    }
}
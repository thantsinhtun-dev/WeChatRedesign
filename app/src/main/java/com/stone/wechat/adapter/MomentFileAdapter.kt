package com.stone.wechat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.R
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.viewholder.MomentFileViewHolder

class MomentFileAdapter: RecyclerView.Adapter<MomentFileViewHolder>() {
    private var mData :List<MomentFileVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentFileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_add_moment_content,parent,false)
        return MomentFileViewHolder(view)
    }

    override fun onBindViewHolder(holder: MomentFileViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data:List<MomentFileVO>){
        mData = data
        notifyDataSetChanged()
    }
}
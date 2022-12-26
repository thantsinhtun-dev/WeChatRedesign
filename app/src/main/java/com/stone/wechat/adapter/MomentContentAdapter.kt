package com.stone.wechat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.R
import com.stone.wechat.data.vos.MomentContentVO
import com.stone.wechat.viewholder.MomentContentViewHolder

class MomentContentAdapter: RecyclerView.Adapter<MomentContentViewHolder>() {
    private var mData :List<String> = listOf()
    private var isMovie:Boolean = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentContentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_moment_content,parent,false)
        return MomentContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: MomentContentViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position],isMovie)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data: List<String>,isMovie:Boolean){
        this.isMovie = isMovie
        mData = data
        notifyDataSetChanged()
    }
}
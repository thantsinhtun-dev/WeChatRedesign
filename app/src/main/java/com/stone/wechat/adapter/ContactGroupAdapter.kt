package com.stone.wechat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.R
import com.stone.wechat.data.vos.GroupVO
import com.stone.wechat.viewholder.ContactGroupViewHolder


class ContactGroupAdapter: RecyclerView.Adapter<ContactGroupViewHolder>() {
    private var mData :List<GroupVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactGroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_group,parent,false)
        return ContactGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactGroupViewHolder, position: Int) {
//        if (mData.isNotEmpty()){
//            holder.bindData(mData[position])
//        }
    }

    override fun getItemCount(): Int {
//        return mData.size
        return  56
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data:List<GroupVO>){
        mData = data
        notifyDataSetChanged()
    }
}
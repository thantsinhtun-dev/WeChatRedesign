package com.stone.wechat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.R
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.delegates.ContactDelegate
import com.stone.wechat.viewholder.ContactViewHolder

class ContactAdapter(private val mDelegate: ContactDelegate,private val isEdit: Boolean) : RecyclerView.Adapter<ContactViewHolder>() {
    private var mData :List<ContactVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_contact,parent,false)
        return ContactViewHolder(view,mDelegate,isEdit)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data:List<ContactVO>){
        mData = data
        notifyDataSetChanged()
    }
}
package com.stone.wechat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.R
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.delegates.SelectedContactDelegate
import com.stone.wechat.viewholder.SelectedContactViewHolder

class SelectedContactAdapter(private val mDelegate: SelectedContactDelegate): RecyclerView.Adapter<SelectedContactViewHolder>() {
    private var mData :List<ContactVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_selected_contacts,parent,false)

        return SelectedContactViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(holder: SelectedContactViewHolder, position: Int) {
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
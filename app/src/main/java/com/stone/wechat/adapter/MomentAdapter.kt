package com.stone.wechat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.R
import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.delegates.MomentDelegate
import com.stone.wechat.viewholder.MomentViewHolder

class MomentAdapter(private val mDelegate:MomentDelegate): RecyclerView.Adapter<MomentViewHolder>() {
    private var mData :List<MomentVO> = listOf()
    private var likeOnly = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_moments,parent,false)
        return MomentViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(
        holder: MomentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)

        if (mData.isNotEmpty()){

            when {
                payloads.isEmpty() -> holder.bindData(mData[position])
                else -> holder.bindLikeAndLikeCount(mData[position])
            }
//            holder.bindData(mData[position])

        }

    }
    override fun onBindViewHolder(holder: MomentViewHolder, position: Int) {
//        if (mData.isNotEmpty()){
//
//                holder.bindData(mData[position])
//
//        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data:List<MomentVO>){
        likeOnly = false
        mData = data
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data:List<MomentVO>,position: Int){
//        likeOnly = true
        mData = data
        notifyItemChanged(position,"fda")
//        notifyDataSetChanged()
    }

}
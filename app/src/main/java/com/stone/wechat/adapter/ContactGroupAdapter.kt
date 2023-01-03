package com.stone.wechat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.stone.wechat.R
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.delegates.ContactDelegate
import com.stone.wechat.utils.getChar
import com.stone.wechat.viewholder.ContactGroupViewHolder
import kotlinx.android.synthetic.main.view_holder_contacts_group.view.*

class ContactGroupAdapter(private val mDelegate: ContactDelegate,private  val isEditMode:Boolean = false) : RecyclerView.Adapter<ContactGroupViewHolder>() {
    private var mData: List<ContactVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactGroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_contacts_group, parent, false)
        return ContactGroupViewHolder(view,isEditMode)
    }

    override fun onBindViewHolder(holder: ContactGroupViewHolder, position: Int) {
        val list = mData.sortedBy { it.contactName }.getContacts(position)





        if (list.isNotEmpty()) {
            holder.itemView.visibility = View.VISIBLE
            val params = holder.itemView.layoutParams
            params.height = RelativeLayout.LayoutParams.WRAP_CONTENT
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT
            holder.itemView.layoutParams = params
            holder.bindData(list, position.getChar(),mDelegate)
        } else {
            holder.itemView.visibility = View.GONE
            val params = holder.itemView.layoutParams
            params.height = 0
            params.width = 0
            holder.itemView.layoutParams = params
        }

//        else holder.itemView.layoutParams = holder.


    }

    override fun getItemCount(): Int {
        return 26
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(data: List<ContactVO>) {
        mData = data
        notifyDataSetChanged()
    }
}

fun List<ContactVO>.getContacts(position: Int): List<ContactVO> {
//    val contactsList : MutableList<ContactVO> =
    return this.filter {
        it.contactName[0].uppercaseChar() == (position + 65).toChar()
    }
}
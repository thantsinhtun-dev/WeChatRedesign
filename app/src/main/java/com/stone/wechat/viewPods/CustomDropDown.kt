package com.stone.wechat.viewPods

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomDropDown(
    context: Context,
    private val resource: Int,
    items: Array<String>,
) : ArrayAdapter<Any?>(context, resource, items) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (position == 0) return View(context)
        return getCustomView(position,convertView,parent)
    }

    private fun getCustomView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?,
    ): View {

        val view = LayoutInflater.from(context).inflate(resource, parent, false) as TextView
        view.text = "${getItem(position)}"
        view.isSingleLine = false



        return view
    }
}
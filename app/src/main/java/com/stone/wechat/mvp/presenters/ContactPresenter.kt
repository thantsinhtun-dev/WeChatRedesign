package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.GroupVO
import com.stone.wechat.delegates.ContactDelegate
import com.stone.wechat.delegates.ContactGroupDelegate
import com.stone.wechat.mvp.views.ContactView

interface ContactPresenter :BasePresenter,ContactDelegate,ContactGroupDelegate{
    fun onUIReady(owner: LifecycleOwner)
    fun initView(view:ContactView)
    fun onTapNewContact()
    fun onTapNewGroup()
    fun addContact(contactUserId:String)
    fun onSearchItem(string: String)
}
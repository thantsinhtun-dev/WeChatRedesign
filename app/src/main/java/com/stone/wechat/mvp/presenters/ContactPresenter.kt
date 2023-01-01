package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.GroupVO
import com.stone.wechat.mvp.views.ContactView

interface ContactPresenter :BasePresenter{
    fun onUIReady(owner: LifecycleOwner)
    fun initView(view:ContactView)
    fun onTapNewContact()
    fun onTapNewGroup()
    fun onTapGroupItem(groupVO: GroupVO)
    fun onTapContactItem(contactVO: ContactVO)
    fun addContact(contactUserId:String)
    fun onSearchItem(string: String)
}
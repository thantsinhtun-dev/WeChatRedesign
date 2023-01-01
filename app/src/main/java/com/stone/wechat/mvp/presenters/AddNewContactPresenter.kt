package com.stone.wechat.mvp.presenters

import com.stone.wechat.mvp.views.AddNewContactView

interface AddNewContactPresenter :BasePresenter{
    fun initView(view:AddNewContactView)
    fun addContact(contactUserId:String)

}
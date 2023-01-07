package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.delegates.ContactDelegate
import com.stone.wechat.delegates.SelectedContactDelegate
import com.stone.wechat.mvp.views.CreateNewGroupView

interface CreateNewGroupPresenter :BasePresenter,ContactDelegate,SelectedContactDelegate{
    fun initView(view:CreateNewGroupView)
    fun onUIReady(owner: LifecycleOwner)
    fun uploadGroupPhoto()
    fun onChangeGroupName(groupName:String)
    fun onTapClearSearch()
    fun onSearchContact(query:String)
    fun onTapCreate(groupName: String)
    fun onTapGroupImage()
    fun selectedGroupImage(file:MomentFileVO)
}
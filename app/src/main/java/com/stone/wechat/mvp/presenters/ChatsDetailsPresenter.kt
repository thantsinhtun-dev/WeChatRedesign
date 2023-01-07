package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.mvp.views.ChatDetailsView

interface ChatsDetailsPresenter :BasePresenter{
    fun initView(view:ChatDetailsView)
    fun onUiReady(contactVO: ContactVO, isGroup: Boolean, owner: LifecycleOwner)
    fun onTapSend(messages:String)
    fun onChooseImage()
    fun onChooseMovie()
    fun selectedContent(selectedContents:List<MomentFileVO>)


}
package com.stone.wechat.mvp.views

import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.MessagesVO
import com.stone.wechat.data.vos.MomentFileVO

interface ChatDetailsView:BaseView {
    fun initChats(messages:List<MessagesVO>,currentUserId:String)
    fun showFailureMessages(messages:String)
    fun showGallery()
    fun showSelectedContents(selectedContents:List<MomentFileVO>)
    fun initContact(contactVO: ContactVO)


}
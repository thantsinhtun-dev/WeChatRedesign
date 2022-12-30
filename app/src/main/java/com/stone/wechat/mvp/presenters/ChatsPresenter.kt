package com.stone.wechat.mvp.presenters

import com.stone.wechat.data.vos.ChatHistoryVO
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.mvp.views.ChatsView

interface ChatsPresenter:BasePresenter {
    fun initView(view:ChatsView)
    fun onTapSearchButton()
    fun onTapActiveUser(userVO: UserVO)
    fun onTapChats(chatHistoryVO: ChatHistoryVO)
}
package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.vos.ChatHistoryVO
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.mvp.views.ChatsView

class ChatsPresenterImpl :ViewModel(),ChatsPresenter{
    private var mView:ChatsView? = null

    override fun initView(view: ChatsView) {
        mView = view
    }

    override fun onTapSearchButton() {
        TODO("Not yet implemented")
    }

    override fun onTapActiveUser(userVO: UserVO) {
        TODO("Not yet implemented")
    }

    override fun onTapChats(chatHistoryVO: ChatHistoryVO) {
        TODO("Not yet implemented")
    }

    override fun onUIReady(owner: LifecycleOwner) {
        mView?.initChats(arrayListOf())
        mView?.initActiveUsers(arrayListOf())
    }

    override fun onTapBackButton() {
        TODO("Not yet implemented")
    }
}
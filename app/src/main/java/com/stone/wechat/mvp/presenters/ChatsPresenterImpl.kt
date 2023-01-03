package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.models.AuthModel
import com.stone.wechat.data.models.AuthModelImpl
import com.stone.wechat.data.models.ChatsModel
import com.stone.wechat.data.models.ChatsModelImpl
import com.stone.wechat.data.vos.ChatHistoryVO
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.mvp.views.ChatsView

class ChatsPresenterImpl :ViewModel(),ChatsPresenter{
    private var mView:ChatsView? = null
    private val mChatModel:ChatsModel = ChatsModelImpl
    private val mAuthModel:AuthModel = AuthModelImpl

    override fun initView(view: ChatsView) {
        mView = view
    }

    override fun onTapSearchButton() {
    }

    override fun onTapActiveUser(userVO: UserVO) {
    }

    override fun onTapChats(chatHistoryVO: ChatHistoryVO) {
    }

    override fun onUIReady(owner: LifecycleOwner) {
        mAuthModel.getCurrentUser(
            onSuccess = { userId->
                mChatModel.getAllChatsMessages(
                    userId,
                    onSuccess = {
                                mView?.initChats(it)
                    }, onFailure = {

                    }
                )
            }, onError = {

            }
        )

        mView?.initChats(arrayListOf())
        mView?.initActiveUsers(arrayListOf())

    }

    override fun onTapBackButton() {

    }
}
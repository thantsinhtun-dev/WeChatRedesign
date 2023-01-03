package com.stone.wechat.mvp.presenters

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.models.AuthModel
import com.stone.wechat.data.models.AuthModelImpl
import com.stone.wechat.data.models.ChatsModel
import com.stone.wechat.data.models.ChatsModelImpl
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.MessagesVO
import com.stone.wechat.mvp.views.ChatDetailsView

class ChatsDetailsPresenterImpl : ViewModel(), ChatsDetailsPresenter {
    private var mView: ChatDetailsView? = null

    private var contactVO: ContactVO? = null
    private val mAuthModel:AuthModel = AuthModelImpl
    private val mChatsModel:ChatsModel = ChatsModelImpl
    private var isGroup = false

    override fun initView(view: ChatDetailsView) {
        mView = view
    }

    override fun onUiReady(contactVO: ContactVO, isGroup: Boolean, owner: LifecycleOwner) {
        this.contactVO = contactVO
        this.isGroup = isGroup

        if (isGroup){
            mAuthModel.getCurrentUser(
                onSuccess = { userId ->
                    this.contactVO?.let { vo ->
                        mChatsModel.getAllGroupMessages(
                            vo.contactId,
                            onSuccess = {
                                if (owner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                                    mView?.initChats(it, userId)
                                }
                            }, onFailure = {
                                mView?.showError(it)
                            }
                        )
                    }
                }, onError = {
                    mView?.showError(it)
                }
            )


        }else {
            mAuthModel.getCurrentUser(
                onSuccess = { userId ->
                    this.contactVO?.let { it1 ->
                        mChatsModel.getChatsMessagesWithContactId(
                            userId, it1.contactId,
                            onSuccess = {
                                if (owner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                                    mView?.initChats(it, userId)
                                }
                            }, onFailure = {

                            }
                        )
                    }
                }, onError = {

                }
            )


        }
        mView?.initContact(contactVO)
    }

    override fun onTapSend(messages: String) {
        if (isGroup){
            mAuthModel.getCurrentUser(
                onSuccess = {
                    this.contactVO?.let { it1 ->
                        mChatsModel.sendGroupMessages(
                            it1.contactId ,
                            messages = MessagesVO(
                                System.currentTimeMillis().toString(),
                                senderName = it1.contactName,
                                senderId = it,
                                senderProfile = it,
                                message = messages,
                                file = arrayListOf(),
                                timeStamp = System.currentTimeMillis(),
                                isMovie = false
                            ), onSuccess = {}, onFailure = {}
                        )
                    }
                }, onError = {

                }
            )

        }else {
            mAuthModel.getCurrentUser(
                onSuccess = {
                    this.contactVO?.let { it1 ->
                        mChatsModel.sendMessages(
                            it, it1.contactId,
                            messages = MessagesVO(
                                System.currentTimeMillis().toString(),
                                senderName = "aung Aung",
                                senderId = it,
                                senderProfile = it,
                                message = messages,
                                file = arrayListOf(),
                                timeStamp = System.currentTimeMillis(),
                                isMovie = false
                            ), onSuccess = {}, onFailure = {}
                        )
                    }
                }, onError = {

                }
            )
        }
    }

    override fun onChooseImage() {
        TODO("Not yet implemented")
    }

    override fun onChooseMovie() {
        TODO("Not yet implemented")
    }

    override fun onTapBackButton() {
        TODO("Not yet implemented")
    }
}
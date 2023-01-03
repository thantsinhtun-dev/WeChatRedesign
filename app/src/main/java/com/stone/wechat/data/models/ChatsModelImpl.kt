package com.stone.wechat.data.models

import com.stone.wechat.data.vos.ChatHistoryVO
import com.stone.wechat.data.vos.MessagesVO
import com.stone.wechat.networks.auth.FirebaseAuthManager
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl
import com.stone.wechat.networks.realtime.FirebaseRealTimeDB
import com.stone.wechat.networks.realtime.FirebaseRealTimeDBImpl

object ChatsModelImpl:ChatsModel {
    override val database: FirebaseRealTimeDB = FirebaseRealTimeDBImpl
    override var mAuthManager: FirebaseAuthManager = FirebaseAuthManagerImpl
    override fun getAllChatsMessages(
        senderId: String,
        onSuccess: (List<ChatHistoryVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.getAllChatsMessages(senderId, onSuccess, onFailure)
    }

    override fun getChatsMessagesWithContactId(
        currentUserId: String,
        contactUserId: String,
        onSuccess: (List<MessagesVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.getChatsMessagesWithContactId(currentUserId,contactUserId, onSuccess, onFailure)
    }

    override fun sendMessages(
        currentUserId: String,
        contactUserId: String,
        messages: MessagesVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.sendMessages(currentUserId, contactUserId, messages, onSuccess, onFailure)
    }

    override fun sendGroupMessages(
        groupId:String,
        messages: MessagesVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
       database.sendGroupMessages(groupId, messages, onSuccess, onFailure)
    }

    override fun getAllGroupMessages(
        groupId: String,
        onSuccess: (List<MessagesVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.getAllGroupMessages(groupId,onSuccess, onFailure)
    }
}
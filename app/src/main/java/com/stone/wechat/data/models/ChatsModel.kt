package com.stone.wechat.data.models

import com.google.firebase.database.DatabaseReference
import com.stone.wechat.data.vos.ChatHistoryVO
import com.stone.wechat.data.vos.MessagesVO
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.auth.FirebaseAuthManager
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl
import com.stone.wechat.networks.realtime.FirebaseRealTimeDB

interface ChatsModel {
    val database: FirebaseRealTimeDB
    var mAuthManager: FirebaseAuthManager
    var mCloudFireStoreApi: CloudFireStoreApi

    fun getAllChatsMessages(
        senderId: String,
        onSuccess: (List<ChatHistoryVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getChatsMessagesWithContactId(
        currentUserId: String,
        contactUserId: String,
        onSuccess: (List<MessagesVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun sendMessages(
        currentUserId: String,
        contactUserId: String,
        files:List<MomentFileVO>,
        messages: MessagesVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun sendGroupMessages(
        groupId:String,
        messages: MessagesVO,
        files:List<MomentFileVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getAllGroupMessages(
        groupId:String,
        onSuccess: (List<MessagesVO>) -> Unit,
        onFailure: (String) -> Unit
    )
}
package com.stone.wechat.data.models

import android.util.Log
import com.stone.wechat.data.vos.ChatHistoryVO
import com.stone.wechat.data.vos.MessagesVO
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl
import com.stone.wechat.networks.auth.FirebaseAuthManager
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl
import com.stone.wechat.networks.realtime.FirebaseRealTimeDB
import com.stone.wechat.networks.realtime.FirebaseRealTimeDBImpl

object ChatsModelImpl:ChatsModel {
    override val database: FirebaseRealTimeDB = FirebaseRealTimeDBImpl
    override var mAuthManager: FirebaseAuthManager = FirebaseAuthManagerImpl
    override var mCloudFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl

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
        database.getChatsMessagesWithContactId(currentUserId,contactUserId, onSuccess={ messages->
            messages.forEach { message->
                mCloudFireStoreApi.getProfileData(message.senderId, onSuccess = { user->
                    message.senderProfile = user.profile
                    message.senderName = user.name
                }, onFailure = {
                    onFailure(it)
                })
            }
            onSuccess(messages)
        }, onFailure)
    }

    override fun sendMessages(
        currentUserId: String,
        contactUserId: String,
        files: List<MomentFileVO>,
        messages: MessagesVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.sendMessages(currentUserId, contactUserId,files, messages, onSuccess, onFailure)
    }

    override fun sendGroupMessages(
        groupId:String,
        messages: MessagesVO,
        files:List<MomentFileVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
       database.sendGroupMessages(groupId, messages,files, onSuccess, onFailure)
    }

    override fun getAllGroupMessages(
        groupId: String,
        onSuccess: (List<MessagesVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.getAllGroupMessages(groupId,onSuccess={ messages->
            var count = 0
            messages.forEach { message->
                mCloudFireStoreApi.getProfileData(message.senderId, onSuccess = { user->
                    message.senderProfile = user.profile
                    message.senderName = user.name
                    count++
                    if (messages.size == count){
                        onSuccess(messages)
                    }
                }, onFailure = {
                    onFailure(it)
                })
                Log.i("groupmessage",message.toString())
            }
//            onSuccess(messages)
        }, onFailure)
    }
}
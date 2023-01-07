package com.stone.wechat.networks.realtime

import com.google.firebase.database.DatabaseReference
import com.stone.wechat.data.vos.ChatHistoryVO
import com.stone.wechat.data.vos.GroupVO
import com.stone.wechat.data.vos.MessagesVO
import com.stone.wechat.data.vos.MomentFileVO

interface FirebaseRealTimeDB {
    val database: DatabaseReference
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
        messages:MessagesVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )
    fun createGroup(
        currentUserId:String,
        groupName:String,
        groupPhoto:String,
        memberList:List<String>,
        onSuccess: (String)->Unit,onFailure:(String)->Unit
    )
    fun getAllGroups( currentUserId: String,onSuccess: (List<GroupVO>)->Unit, onFailure:(String)->Unit)

    fun sendGroupMessages(
        groupId:String,
        messages:MessagesVO,
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
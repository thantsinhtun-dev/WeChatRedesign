package com.stone.wechat.data.models

import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.GroupVO
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl
import com.stone.wechat.networks.realtime.FirebaseRealTimeDB

interface ContactModel {
    var mFireStoreApi: CloudFireStoreApi
    var mAuthManager: FirebaseAuthManagerImpl
    val database: FirebaseRealTimeDB

    fun addContacts(contactUserId:String,onSuccess: (List<ContactVO>)->Unit,onFailure:(String)->Unit)
    fun getAllContacts(onSuccess: (List<ContactVO>)->Unit,onFailure:(String)->Unit)
    fun createGroup(
        groupName:String,
        groupPhoto: MomentFileVO?,
        memberList:List<String>,
        onSuccess: (String)->Unit, onFailure:(String)->Unit
    )
    fun getAllGroups(onSuccess: (List<GroupVO>)->Unit,onFailure:(String)->Unit)
    fun changeOnlineStatus(isOnline:Boolean,onSuccess: (String) -> Unit,onFailure: (String) -> Unit)
}
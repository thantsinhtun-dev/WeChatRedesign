package com.stone.wechat.data.models

import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl

interface ContactModel {
    var mFireStoreApi: CloudFireStoreApi
    var mAuthManager: FirebaseAuthManagerImpl
    fun addContacts(contactUserId:String,onSuccess: (List<ContactVO>)->Unit,onFailure:(String)->Unit)
    fun getAllContacts(onSuccess: (List<ContactVO>)->Unit,onFailure:(String)->Unit)
}
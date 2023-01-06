package com.stone.wechat.data.models

import android.util.Log
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.GroupVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl
import com.stone.wechat.networks.realtime.FirebaseRealTimeDB
import com.stone.wechat.networks.realtime.FirebaseRealTimeDBImpl

object ContactModelImpl : ContactModel {
    override var mFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl
    override var mAuthManager: FirebaseAuthManagerImpl = FirebaseAuthManagerImpl
    override val database: FirebaseRealTimeDB = FirebaseRealTimeDBImpl

    override fun addContacts(
        contactUserId: String,
        onSuccess: (List<ContactVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.getCurrentUser(
            onSuccess = {
                Log.i("currentUserId", it)
                mFireStoreApi.addContacts(it, contactUserId, onSuccess, onFailure)
            },
            onFailure = {
                onFailure(it)
            }
        )
    }

    override fun getAllContacts(onSuccess: (List<ContactVO>) -> Unit, onFailure: (String) -> Unit) {
        mAuthManager.getCurrentUser(
            onSuccess = {
                mFireStoreApi.getAllContacts(it, onSuccess, onFailure)
            },
            onFailure = {
                onFailure(it)
            }
        )
    }

    override fun createGroup(
        groupName: String,
        groupPhoto: String,
        memberList: List<String>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.getCurrentUser(
            onSuccess = {
                database.createGroup(it, groupName, groupPhoto, memberList, onSuccess, onFailure)
            },
            onFailure = {
                onFailure(it)
            }
        )
    }

    override fun getAllGroups(onSuccess: (List<GroupVO>) -> Unit, onFailure: (String) -> Unit) {
        mAuthManager.getCurrentUser(
            onSuccess = {
                database.getAllGroups(currentUserId = it, onSuccess, onFailure)
            }, onFailure = {
                onFailure(it)
            }
        )
    }
}
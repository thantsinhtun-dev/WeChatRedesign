package com.stone.wechat.data.models

import android.util.Log
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl

object ContactModelImpl : ContactModel {
    override var mFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl
    override var mAuthManager: FirebaseAuthManagerImpl = FirebaseAuthManagerImpl

    override fun addContacts(
        contactUserId: String,
        onSuccess: (List<ContactVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.getCurrentUser(
            onSuccess = {
                Log.i("currentUserId",it)
                        mFireStoreApi.addContacts(it,contactUserId, onSuccess,onFailure)
            },
            onError = {
                onFailure(it)
            }
        )
    }

    override fun getAllContacts(onSuccess: (List<ContactVO>) -> Unit, onFailure: (String) -> Unit) {
        mAuthManager.getCurrentUser(
            onSuccess = {
                mFireStoreApi.getAllContacts(it,onSuccess, onFailure)
            },
            onError = {
                onFailure(it)
            }
        )
    }
}
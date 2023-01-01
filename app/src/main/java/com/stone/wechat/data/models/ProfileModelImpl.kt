package com.stone.wechat.data.models

import android.graphics.Bitmap
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl
import com.stone.wechat.networks.auth.FirebaseAuthManager
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl

object ProfileModelImpl : ProfileModel {
    override var mFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl
    override var mAuthManager: FirebaseAuthManager = FirebaseAuthManagerImpl

    override fun getProfileData(
        userId: String,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.getCurrentUser(
            onSuccess = {
                mFireStoreApi.getProfileData(it, onSuccess, onFailure)
            },
            onError = {
                onFailure(it)
            }
        )
    }

    override fun updateProfileData(
        userVO: UserVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFireStoreApi.updateProfileData(userVO, onSuccess, onFailure)
    }

    override fun updateProfileImage(
        image: Bitmap,
        userVO: UserVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFireStoreApi.updateProfileImage(image, userVO, onSuccess, onFailure)
    }
}
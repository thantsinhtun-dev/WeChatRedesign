package com.stone.wechat.data.models

import android.graphics.Bitmap
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl

object ProfileModelImpl :ProfileModel{
    override var mFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl

    override fun getProfileData(
        userId: String,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFireStoreApi.getProfileData(userId, onSuccess, onFailure)
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
        mFireStoreApi.updateProfileImage(image,  userVO, onSuccess, onFailure)
    }
}
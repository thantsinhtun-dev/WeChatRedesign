package com.stone.wechat.data.models

import android.graphics.Bitmap
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.auth.AuthManager
import com.stone.wechat.networks.auth.FirebaseAuthManager

interface ProfileModel {
    var mFireStoreApi: CloudFireStoreApi
    fun getProfileData(userId: String, onSuccess: (UserVO) -> Unit, onFailure: (String) -> Unit)
    fun updateProfileData(userVO: UserVO,onSuccess: (String) -> Unit,onFailure: (String) -> Unit)
    fun updateProfileImage(image: Bitmap,userVO: UserVO,onSuccess: (String) -> Unit,onFailure: (String) -> Unit)

}
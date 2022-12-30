package com.stone.wechat.networks

import android.graphics.Bitmap
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.data.vos.UserVO

interface CloudFireStoreApi {
    fun createUser(
        userId:String,
        name: String,
        phone: String,
        dob: String,
        gender: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun checkPhoneNumber(
        phone: String,
        exists: () -> Unit,
        notExists: () -> Unit
    )

    fun login(
        phone: String, password: String,
        onSuccess: () -> Unit,
        onFailure: (message:String) -> Unit
    )
    fun createMoment(
        momentText:String,
        momentContents:List<MomentFileVO>,
        onSuccess: () -> Unit,
        onFailure: (errorMessage:String) -> Unit
    )
    fun getMoments(
        onSuccess: (List<MomentVO>) -> Unit,
        onFailure: (errorMessage:String) -> Unit
    )
    fun getCurrentUserFromFireStore(userId:String, onSuccess: (UserVO) -> Unit, onError: (errorMessage: String?) -> Unit)


    fun getProfileData(userId: String, onSuccess: (UserVO) -> Unit, onFailure: (String) -> Unit)
    fun updateProfileData(userVO: UserVO,onSuccess: (String) -> Unit,onFailure: (String) -> Unit)
    fun updateProfileImage(image: Bitmap, userVO: UserVO, onSuccess: (String) -> Unit, onFailure: (String) -> Unit)



}
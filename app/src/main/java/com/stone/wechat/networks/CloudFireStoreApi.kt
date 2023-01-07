package com.stone.wechat.networks

import android.graphics.Bitmap
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.data.vos.UserVO

interface CloudFireStoreApi {
    fun createUser(
        userId: String,
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
        onFailure: (message: String) -> Unit
    )

    fun createMoment(
        userVO: UserVO,
        momentText: String,
        momentContents: List<MomentFileVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMoments(
        onSuccess: (List<MomentVO>) -> Unit,
        onFailure: (errorMessage: String) -> Unit
    )

    fun getCurrentUserFromFireStore(
        userId: String,
        onSuccess: (UserVO) -> Unit,
        onError: (errorMessage: String?) -> Unit
    )


    ///profile
    fun getProfileData(userId: String, onSuccess: (UserVO) -> Unit, onFailure: (String) -> Unit)
    fun updateProfileData(userVO: UserVO, onSuccess: (String) -> Unit, onFailure: (String) -> Unit)
    fun updateProfileImage(
        image: Bitmap,
        userVO: UserVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addContacts(
        currentUserId: String,
        addUserId: String,
        onSuccess: (List<ContactVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getAllContacts(
        currentUserId: String,
        onSuccess: (List<ContactVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getAllMoments(
        userId: String,
        onTapLikeCallBack: (MomentVO) -> Unit,
        onSuccess: (List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun handleLike(
        userId: String,
        isRemoveLike: Boolean,
        momentId: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit,
    )
    fun saveMoment(
        userId: String,
        momentId: String,
        isSaveMoment:Boolean,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )



}
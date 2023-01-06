package com.stone.wechat.data.models

import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl

interface MomentModel {
    var mAuthManager: FirebaseAuthManagerImpl
    var mFireStoreApi: CloudFireStoreApi
    fun createMoment(
        userVO: UserVO,
        momentText: String,
        momentContents: List<MomentFileVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getAllMoments(
        onTapLikeCallBack: (MomentVO) -> Unit,
        onSuccess: (List<MomentVO>) -> Unit, onFailure: (String) -> Unit
    )

    fun handleLike(
        momentId: String,
        isRemoveLike: Boolean,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit,
    )
}
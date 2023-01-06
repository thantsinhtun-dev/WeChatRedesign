package com.stone.wechat.data.models

import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl

object MomentModelImpl : MomentModel {
    override var mFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl
    override var mAuthManager: FirebaseAuthManagerImpl = FirebaseAuthManagerImpl

    override fun createMoment(
        userVO: UserVO,
        momentText: String,
        momentContents: List<MomentFileVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFireStoreApi.createMoment(userVO, momentText, momentContents, onSuccess, onFailure)
    }

    override fun getAllMoments(
        onTapLikeCallBack: (MomentVO) -> Unit,
        onSuccess: (List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.getCurrentUser(
            onSuccess = { userId ->
                mFireStoreApi.getAllMoments(userId, onTapLikeCallBack, onSuccess, onFailure)
            },
            onFailure = {
                onFailure(it)
            }
        )

    }

    override fun handleLike(
        momentId: String,
        isRemoveLike: Boolean,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.getCurrentUser(
            onSuccess = { userId ->
                mFireStoreApi.handleLike(userId, isRemoveLike, momentId, onSuccess, onFailure)
            },
            onFailure = {
                onFailure(it)
            }
        )
    }

}
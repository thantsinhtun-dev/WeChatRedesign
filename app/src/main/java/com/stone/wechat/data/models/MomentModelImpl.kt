package com.stone.wechat.data.models

import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl

object MomentModelImpl :MomentModel{
    override var mFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl

    override fun createMoment(
        userVO: UserVO,
        momentText: String,
        momentContents: List<MomentFileVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFireStoreApi.createMoment(userVO, momentText, momentContents, onSuccess, onFailure)
    }

}
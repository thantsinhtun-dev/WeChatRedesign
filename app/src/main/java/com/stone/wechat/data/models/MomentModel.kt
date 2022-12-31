package com.stone.wechat.data.models

import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.networks.CloudFireStoreApi

interface MomentModel {
    var mFireStoreApi: CloudFireStoreApi
    fun createMoment(userVO: UserVO,momentText:String,momentContents:List<MomentFileVO>,onSuccess:(String)->Unit,onFailure:(String)->Unit)
}
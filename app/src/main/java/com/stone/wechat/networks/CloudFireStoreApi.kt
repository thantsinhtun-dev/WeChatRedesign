package com.stone.wechat.networks

import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.data.vos.MomentVO

interface CloudFireStoreApi {
    fun createUser(
        name: String,
        phone: String,
        dob: String,
        gender: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
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

}
package com.stone.wechat.networks

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

}
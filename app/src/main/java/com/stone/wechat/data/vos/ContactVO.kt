package com.stone.wechat.data.vos

import java.io.Serializable

data class ContactVO(
    val contactId:String,
    val contactName:String,
    val contactImage:String,
    val isFavourite:String,
    var isSelected:Boolean = false,
    var onlineStatus:Boolean = false,
    var lastOnlineTime:Long = 0
):Serializable
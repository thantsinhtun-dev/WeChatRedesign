package com.stone.wechat.data.vos

data class MessagesVO(
    val chatId:String? = "",
    val senderName:String? = "",
    val senderProfile:String? = "",
    var file:List<String>? = arrayListOf(),
    val message:String? = "",
    val timeStamp:Long? = 0,
    val senderId:String? = "",
    var isMovie: Boolean = false
)
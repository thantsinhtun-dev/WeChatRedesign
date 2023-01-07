package com.stone.wechat.data.vos

data class MessagesVO(
    val chatId:String? = "",
    var senderName:String? = "",
    var senderProfile:String? = "",
    var file:List<String>? = arrayListOf(),
    val message:String? = "",
    val timeStamp:Long? = 0,
    val senderId:String = "",
    var isMovie: Boolean = false
)
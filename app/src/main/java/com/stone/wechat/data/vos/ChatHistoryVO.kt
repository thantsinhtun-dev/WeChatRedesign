package com.stone.wechat.data.vos

data class ChatHistoryVO(
    var userId:String? = "",

    var messages:MessagesVO? = MessagesVO()
)
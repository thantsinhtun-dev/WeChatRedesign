package com.stone.wechat.data.vos

data class GroupVO(
    val groupId:String = "",
    val groupName:String = "",
    val groupPhoto:String = "",
    val memberList:List<String> = arrayListOf(),
)
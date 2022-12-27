package com.stone.wechat.data.vos

data class MomentVO(
    val userId:String?,
    val userName:String?,
    val profileImage:String?,
    val time: Long,
    val isMovie:Boolean = false,
    val content: List<String>?,
    val momentText:String?,
    val imageList:List<String>?,

    )
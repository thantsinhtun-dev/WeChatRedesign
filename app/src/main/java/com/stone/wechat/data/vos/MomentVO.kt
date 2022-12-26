package com.stone.wechat.data.vos

data class MomentVO(
    val id:String?,
    val name:String?,
    val time: Long,
    val isMovie:Boolean = false,
    val content: List<String>?,
    val momentText:String?,
    val imageList:List<String>?,
    val profileImage:String?,

    )
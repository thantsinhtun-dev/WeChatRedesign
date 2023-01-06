package com.stone.wechat.data.vos

data class MomentVO(
    var momentId:String = "",
    var userId:String? = "",
    var userName:String? = "",
    var profileImage:String? = "",
    var time: Long? = 0,
    var isMovie:Boolean = false,
    var content: List<String>? = null,
    var momentText:String? = "",
    var imageList:List<String>? = null,
    var likeCount:Int = 0,
    var commentCount:Int = 0,
    var isLiked:Boolean = false,
    var isSaved:Boolean = false

    )
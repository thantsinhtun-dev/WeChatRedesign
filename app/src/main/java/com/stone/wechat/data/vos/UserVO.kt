package com.stone.wechat.data.vos

data class UserVO(
    var name:String? = "",
    var phone:String? = "",
    var dob:String? = "",
    var gender:String? = "",
    var password:String? = "",
    var userId:String? = "",
    var qrCode:String? = "",
    var profile:String? = "",
    var onlineStatus:Boolean = false,
    var lastOnlineTime:Long = 0

)
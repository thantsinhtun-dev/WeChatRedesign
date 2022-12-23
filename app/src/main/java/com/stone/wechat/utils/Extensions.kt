package com.stone.wechat.utils

import com.stone.wechat.WeChatApplication

fun getString(id:Int): String{
   return WeChatApplication().resources.getString(id)
}
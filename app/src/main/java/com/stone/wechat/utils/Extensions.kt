package com.stone.wechat.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.bumptech.glide.Glide
import com.stone.wechat.WeChatApplication

fun getString(id:Int): String{
   return WeChatApplication().resources.getString(id)
}

fun Uri.loadBitmapFromUri(context: Context) : Bitmap {
   return Glide.with(context)
      .asBitmap()
      .load(this)
      .submit()
      .get()
}
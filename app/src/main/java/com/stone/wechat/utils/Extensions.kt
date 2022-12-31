package com.stone.wechat.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
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

fun String.getQrCodeBitmap(): Bitmap {
   val size = 512 //pixels]
   val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
   val bits = QRCodeWriter().encode(this, BarcodeFormat.QR_CODE, size, size,hints)
   return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
      for (x in 0 until size) {
         for (y in 0 until size) {
            it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
         }
      }
   }
}
fun Int.getChar():String{
  return (this+65).toChar().toString()
}
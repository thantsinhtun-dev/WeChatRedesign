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
import java.text.SimpleDateFormat
import java.util.*

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

fun Long.formatDate(): String {
   return SimpleDateFormat("HH:mm a", Locale.US).format(Date(this)).toString()
}
private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS


fun Long.getTimeAgo(): String? {
   var time = this
   if (time < 1000000000000L) {
      // if timestamp given in seconds, convert to millis
      time *= 1000
   }
   val now: Long = System.currentTimeMillis()
   if (time > now || time <= 0) {
      return null
   }

   // TODO: localize
   val diff = now - time
   return if (diff < MINUTE_MILLIS) {
      "a moment ago"
   } else if (diff < 2 * MINUTE_MILLIS) {
      "a minute ago"
   } else if (diff < 50 * MINUTE_MILLIS) {
      (diff / MINUTE_MILLIS).toString() + " minutes ago"
   } else if (diff < 90 * MINUTE_MILLIS) {
      "an hour ago"
   } else if (diff < 24 * HOUR_MILLIS) {

      (diff/HOUR_MILLIS).toString() + " hours ago"
   } else if (diff < 48 * HOUR_MILLIS) {
      "yesterday"
   } else {
      (diff / DAY_MILLIS).toString() + " days ago"
   }
}
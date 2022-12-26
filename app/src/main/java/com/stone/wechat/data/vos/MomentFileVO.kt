package com.stone.wechat.data.vos

import android.graphics.Bitmap
import android.net.Uri

data class MomentFileVO(
    var filePath:String,
    var content:Bitmap,
    var isMovie: Boolean = false,
    var moviePath: Uri?
)
package com.stone.wechat.delegates

import com.stone.wechat.data.vos.MomentVO

interface MomentDelegate {
    fun onTapLike(mMomentVO: MomentVO, absoluteAdapterPosition: Int)
    fun onTapComment()
    fun onTapBookMark(vo: MomentVO, absoluteAdapterPosition: Int)
    fun onTapImage()
}
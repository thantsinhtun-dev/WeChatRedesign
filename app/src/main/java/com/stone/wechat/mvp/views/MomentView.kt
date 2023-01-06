package com.stone.wechat.mvp.views

import com.stone.wechat.data.vos.MomentVO

interface MomentView:BaseView {
    fun initMoment(moments: List<MomentVO>)
    fun navigateToCreateMoment()
    fun updateLikeCount(moments: List<MomentVO>,position: Int)
}
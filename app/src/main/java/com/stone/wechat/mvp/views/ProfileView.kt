package com.stone.wechat.mvp.views

import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.data.vos.UserVO

interface ProfileView :BaseView{

    fun initProfile(userVO: UserVO)
    fun initBookMarkMoment(moments:List<MomentVO>)
    fun showEditUserInfoDialog(userVO: UserVO)
    fun pickProfileImage()
    fun showQrCode(qrCode: String)
    fun changeProfileImage(url:String)
    fun initMoment(moments: List<MomentVO>)
    fun updateLikeCount(moments: List<MomentVO>, position: Int)

}
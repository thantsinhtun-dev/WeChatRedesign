package com.stone.wechat.mvp.presenters

import com.stone.wechat.mvp.views.VerificationView

interface VerificationPresenter:BasePresenter {
    fun initView(view:VerificationView)
    fun onTapGetOtp(phone:String)
    fun onTapVerify(otp:String)
    fun onChangePhoneNumber(phone: String)
    fun onChangeOtp(otp:String)
}
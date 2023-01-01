package com.stone.wechat.mvp.presenters

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.stone.wechat.mvp.views.VerificationView

interface VerificationPresenter:BasePresenter {
    fun onUIReady(owner: LifecycleOwner)
    fun initView(view:VerificationView)
    fun onTapGetOtp(activity: AppCompatActivity)
    fun onTapVerify(otp:String)
    fun onChangePhoneNumber(phone: String)
    fun onChangeOtp(otp:String)

}
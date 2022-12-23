package com.stone.wechat.mvp.presenters

import androidx.appcompat.app.AppCompatActivity
import com.stone.wechat.mvp.views.VerificationView

interface VerificationPresenter:BasePresenter {
    fun initView(view:VerificationView)
    fun onTapGetOtp(phone:String,activity: AppCompatActivity)
    fun onTapVerify(name:String,phone: String,dob:String,gender:String,password:String,otp:String)
    fun onChangePhoneNumber(phone: String)
    fun onChangeOtp(otp:String)

}
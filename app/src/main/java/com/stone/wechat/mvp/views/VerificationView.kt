package com.stone.wechat.mvp.views

interface VerificationView :BaseView{
    fun navigateToSignUpActivity(phone: String, userId: String)
    fun activateOTPButton(activate:Boolean)
    fun activateVerifyButton(activate: Boolean)
    fun showErrorMessage(title:String,body:String)
    fun showMessage(message:String)
}
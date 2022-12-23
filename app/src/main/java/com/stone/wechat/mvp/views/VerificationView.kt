package com.stone.wechat.mvp.views

interface VerificationView :BaseView{
    fun navigateToMainActivity()
    fun activateOTPButton(activate:Boolean)
    fun activateVerifyButton(activate: Boolean)
    fun showErrorMessage(title:String,body:String)
    fun showMessage(message:String)
}
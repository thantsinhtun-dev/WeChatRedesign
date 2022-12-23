package com.stone.wechat.mvp.views

interface VerificationView :BaseView{
    fun navigateToMainActivity()
    fun activateOTPButton(activate:Boolean)
    fun activateVerifyButton(activate: Boolean)
}
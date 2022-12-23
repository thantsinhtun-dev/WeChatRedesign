package com.stone.wechat.mvp.views

interface LoginView :BaseView{
    fun navigateToMainActivity()
    fun navigateToVerification()
    fun activateLoginButton(boolean: Boolean)
    fun showErrorMessage(title:String,body:String)

}
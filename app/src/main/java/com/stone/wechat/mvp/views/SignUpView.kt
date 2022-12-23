package com.stone.wechat.mvp.views

interface SignUpView :BaseView{
    fun navigateToVerification(name:String,dob:String,gender:String,password:String)
    fun navigateToLandingScreen()
    fun activateSignUpButton(activate: Boolean)
    fun showErrorDialog()
}
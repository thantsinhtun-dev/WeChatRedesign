package com.stone.wechat.mvp.views

interface SignUpView :BaseView{
    fun navigateToMainActivity()
    fun navigateToLandingScreen()
    fun activateSignUpButton(activate: Boolean)
    fun showErrorDialog()
}
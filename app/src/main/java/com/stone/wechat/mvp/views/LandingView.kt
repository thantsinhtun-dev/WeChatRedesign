package com.stone.wechat.mvp.views

interface LandingView :BaseView{
    fun navigateToSignUp()
    fun navigateToLogin()
    fun userAlreadyExists()
    fun userDoesNotExists()
}
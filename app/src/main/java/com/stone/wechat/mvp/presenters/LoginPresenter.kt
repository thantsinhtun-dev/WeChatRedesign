package com.stone.wechat.mvp.presenters

import com.stone.wechat.mvp.views.LoginView

interface LoginPresenter :BasePresenter{
    fun initView(view:LoginView)
    fun onChangePhoneNumber(phone: String)
    fun onChangePassword(password:String)
    fun onTapLogin()
    fun onTapForgetPassword()

}
package com.stone.wechat.mvp.presenters

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.stone.wechat.mvp.views.LoginView

interface LoginPresenter :BasePresenter{
    fun onUIReady(context: Context,lifecycleOwner: LifecycleOwner)
    fun initView(view:LoginView)
    fun onChangePhoneNumber(phone: String)
    fun onChangePassword(password:String)
    fun onTapLogin()
    fun onTapForgetPassword()

}
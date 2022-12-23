package com.stone.wechat.mvp.presenters

import com.stone.wechat.mvp.views.LandingView

interface LandingPresenter:BasePresenter {
    fun initView(view:LandingView)
    fun onTapSignUp()
    fun onTapLogin()
}
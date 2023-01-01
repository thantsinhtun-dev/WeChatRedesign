package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.stone.wechat.mvp.views.LandingView

interface LandingPresenter:BasePresenter {
    fun onUIReady(owner: LifecycleOwner)
    fun initView(view:LandingView)
    fun onTapSignUp()
    fun onTapLogin()
}
package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.mvp.views.LandingView

class LandingPresenterImpl: ViewModel(),LandingPresenter {
    private var mView:LandingView? = null
    override fun initView(view: LandingView) {
        mView = view
    }

    override fun onTapSignUp() {
        mView?.navigateToSignUp()
    }

    override fun onTapLogin() {
        mView?.navigateToLogin()
    }

    override fun onUIReady(owner: LifecycleOwner) {
    }

    override fun onTapBackButton() {
        mView?.onTapBack()
    }
}
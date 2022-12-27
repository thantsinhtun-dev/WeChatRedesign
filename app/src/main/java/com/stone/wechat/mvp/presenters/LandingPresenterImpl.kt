package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.mvp.views.LandingView
import com.stone.wechat.networks.auth.AuthManager
import com.stone.wechat.networks.auth.FirebaseAuthManager

class LandingPresenterImpl: ViewModel(),LandingPresenter {
    private var mView:LandingView? = null
    private var mAuthManager: AuthManager = FirebaseAuthManager
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
        mAuthManager.checkCurrentUser(
            onSuccess = {
                mView?.userAlreadyExists()
            },
            onError = {
                mView?.userDoesNotExists()
            }
        )
    }

    override fun onTapBackButton() {
        mView?.onTapBack()
    }
}
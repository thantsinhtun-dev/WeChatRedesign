package com.stone.wechat.mvp.presenters

import android.os.Build.VERSION_CODES.M
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.models.MomentModel
import com.stone.wechat.data.models.MomentModelImpl
import com.stone.wechat.mvp.views.LandingView
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl

class LandingPresenterImpl: ViewModel(),LandingPresenter {
    private var mView:LandingView? = null
    private var mAuthManager: FirebaseAuthManagerImpl =
        FirebaseAuthManagerImpl
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
            onFailure = {
                mView?.userDoesNotExists()
            }
        )
    }

    override fun onTapBackButton() {
        mView?.onTapBack()
    }
}
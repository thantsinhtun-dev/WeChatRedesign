package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.mvp.views.LoginView

class LoginPresenterImpl : ViewModel(), LoginPresenter {
    private var mView: LoginView? = null
    private var phone: String = ""
    private var password: String = ""
    override fun initView(view: LoginView) {
        mView = view
    }

    override fun onChangePhoneNumber(phone: String) {
        this.phone = phone
        checkPhoneNumber()
    }

    override fun onChangePassword(password: String) {
        this.password = password
        checkPhoneNumber()
    }

    override fun onTapLogin() {

    }

    override fun onTapForgetPassword() {
    }

    override fun onUIReady(owner: LifecycleOwner) {
        checkPhoneNumber()
    }

    override fun onTapBackButton() {
        mView?.onTapBack()
    }

    private fun checkPhoneNumber() {
        mView?.activateLoginButton(phone.isNotEmpty() && password.isNotEmpty())
    }
}
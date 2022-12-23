package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.mvp.views.LoginView
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl

class LoginPresenterImpl : ViewModel(), LoginPresenter {
    private var mView: LoginView? = null
    private var phone: String = ""
    private var password: String = ""

    private val mFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl
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
        mFireStoreApi.login(phone, password,
            onSuccess = {
                mView?.navigateToMainActivity()
            },
            onFailure = {
                mView?.showErrorMessage("Error !",it)
            }
        )
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
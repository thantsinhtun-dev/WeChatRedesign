package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.mvp.views.LoginView
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl
import com.stone.wechat.networks.auth.AuthManager
import com.stone.wechat.networks.auth.FirebaseAuthManager

class LoginPresenterImpl : ViewModel(), LoginPresenter {
    private var mView: LoginView? = null
    private var phone: String = ""
    private var password: String = ""

    private val mFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl
    private var mAuthManager: AuthManager = FirebaseAuthManager

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
        mAuthManager.loginWithEmail(phone,password,
            onSuccess = {
                mView?.navigateToMainActivity()
            },
            onError = {
                mView?.showErrorMessage("Error !",it ?: "Oops somethings wrong")
            })
//        mFireStoreApi.login(phone, password,
//            onSuccess = {
//
//            },
//            onFailure = {
//
//            }
//        )
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
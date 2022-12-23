package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.mvp.views.VerificationView

class VerificationPresenterImpl : ViewModel(), VerificationPresenter {
    private var mView: VerificationView? = null

    private var phone: String = ""
    private var otp: String = ""

    override fun initView(view: VerificationView) {
        mView = view
    }

    override fun onTapGetOtp(phone: String) {

    }

    override fun onTapVerify(otp: String) {

    }

    override fun onChangePhoneNumber(phone: String) {

        this.phone = phone

        checkPhoneNumber()
    }

    override fun onChangeOtp(otp: String) {
        this.otp = otp
        checkOtp()
    }

    override fun onUIReady(owner: LifecycleOwner) {
        checkPhoneNumber()
        checkOtp()
    }

    override fun onTapBackButton() {
        mView?.onTapBack()
    }

    private fun checkPhoneNumber() {
        mView?.activateOTPButton(phone.isNotEmpty())
    }

    private fun checkOtp() {
        mView?.activateVerifyButton(phone.isNotEmpty() && otp.isNotEmpty() && otp.length ==4)
    }
}
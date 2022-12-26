package com.stone.wechat.mvp.presenters

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.stone.wechat.R
import com.stone.wechat.mvp.views.VerificationView
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl.checkPhoneNumber
import com.stone.wechat.networks.auth.AuthManager
import com.stone.wechat.networks.auth.FirebaseAuthManager

class VerificationPresenterImpl : ViewModel(), VerificationPresenter {
    private var mView: VerificationView? = null
    private var mAuthManager: AuthManager = FirebaseAuthManager
    private var mFireBaseApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl

    private var phone: String = ""
    private var otp: String = ""

    private var verificationID = ""

    override fun initView(view: VerificationView) {
        mView = view
    }

    override fun onTapGetOtp(phone: String, activity: AppCompatActivity) {
        Log.i("phoneNumber", phone)
        mFireBaseApi.checkPhoneNumber(
            phone,
            exists = {
                mView?.showErrorMessage(title = "ERROR !", body = "Phone number already exists !")
            },
            notExists = {

                mAuthManager.sendVerificationCode(phone, activity, mCallBack)
//                mView?.showMessage("code was sent!")
            })
    }

    override fun onTapVerify(
        name: String,
        phone: String,
        dob: String,
        gender: String,
        password: String,
        otp: String
    ) {
//        val credential = PhoneAuthProvider.getCredential(verificationID, otp)
        if(otp == "222222") {
            mFireBaseApi.createUser(
                name,
                phone,
                dob,
                gender,
                password,
                onSuccess = { mView?.navigateToMainActivity() },
                onFailure = {
                    mView?.showErrorMessage(
                        "Error !",
                        "Oops, something went wrong, please try again later."
                    )
                })
        }else{
            mView?.showErrorMessage(
                "Error !",
                "Your OTP is incorrect, please try again "
            )
        }
//        mAuthManager.verifyOtpWithCredential(credential,
//            onSuccess = {
//
//
//            },
//            onError = {
//                mView?.showErrorMessage("Error !", it.toString() ?: "Opt is incorrect !")
//            }
//        )
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
        mView?.activateVerifyButton(phone.isNotEmpty() && otp.isNotEmpty() && otp.length == 6)
    }

    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                verificationID = s
                mView?.showMessage("Code was sent!")
                Log.i("otp", s)
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                if (code != null) {
//                    verifyCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.i("otpError", e.toString())
                mView?.showErrorMessage(
                    "Error !",
                    e.localizedMessage ?:
                    "Oops, something went wrong, please try again later."
                )
            }
        }
}
package com.stone.wechat.mvp.presenters

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.stone.wechat.mvp.views.VerificationView
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl

class VerificationPresenterImpl : ViewModel(), VerificationPresenter {
    private var mView: VerificationView? = null
    private var mAuthManager: FirebaseAuthManagerImpl =
        FirebaseAuthManagerImpl
    private var mFireBaseApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl

    private var phone: String = ""
    private var otp: String = ""

    private var verificationID = "kjkjkj"

    override fun initView(view: VerificationView) {
        mView = view
    }

    override fun onTapGetOtp(activity: AppCompatActivity) {

//        mFireBaseApi.checkPhoneNumber(
//            this.phone,
//            exists = {
//                mView?.showErrorMessage(title = "ERROR !", body = "Phone number already exists !")
//            },
//            notExists = {
                mAuthManager.sendVerificationCode(this.phone, activity, mCallBack)
//                mView?.showMessage("code was sent!")
//            })
    }

    override fun onTapVerify(otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationID, otp)

        mAuthManager.verifyOtpWithCredential(credential,
            onSuccess = {
                mView?.navigateToSignUpActivity(phone,it)
            },
            onError = {
                mView?.showErrorMessage("Error !", it.toString() ?: "Opt is incorrect !")
            }
        )
//        mFireBaseApi.createUser(
//            name,
//            phone,
//            dob,
//            gender,
//            password,
//            onSuccess = {
//
//            },
//            onFailure = {
//                mView?.showErrorMessage(
//                    "Error !",
//                    "Phone number already exists!"
//                )
//            })


    }

    override fun onChangePhoneNumber(phone: String) {
        this.phone = phone
        if(this.phone[0].toString()=="0"){
            this.phone = this.phone.replaceRange(0,1,"+95")
        }
        Log.i("phone_number",this.phone)

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
                    e.localizedMessage ?: "Oops, something went wrong, please try again later."
                )
            }
        }
}
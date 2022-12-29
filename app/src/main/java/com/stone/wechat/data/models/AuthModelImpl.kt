package com.stone.wechat.data.models

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.stone.wechat.networks.auth.AuthManager
import com.stone.wechat.networks.auth.FirebaseAuthManager

object AuthModelImpl :AuthModel{
    override var mAuthManager: AuthManager = FirebaseAuthManager

    override fun sendVerificationCode(
        number: String,
        activity: AppCompatActivity,
        mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        mAuthManager.sendVerificationCode(number, activity, mCallBack)
    }

    override fun verifyOtpWithCredential(
        credential: PhoneAuthCredential,
        onSuccess: (String) -> Unit,
        onError: (errorMessage: String?) -> Unit
    ) {
        mAuthManager.verifyOtpWithCredential(credential, onSuccess, onError)
    }

    override fun checkCurrentUser(
        onSuccess: (String) -> Unit,
        onError: (errorMessage: String?) -> Unit
    ) {
        mAuthManager.checkCurrentUser(onSuccess, onError)
    }

    override fun createUserWithEmail(
        phone: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: (errorMessage: String?) -> Unit
    ) {
        mAuthManager.createUserWithEmail(phone, password, onSuccess, onError)
    }

    override fun loginWithEmail(
        phone: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: (errorMessage: String?) -> Unit
    ) {
        mAuthManager.loginWithEmail(phone, password, onSuccess, onError)
    }
}
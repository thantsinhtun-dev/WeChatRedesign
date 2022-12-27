package com.stone.wechat.networks.auth

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

interface AuthManager {
    fun sendVerificationCode(number:String,activity:AppCompatActivity,mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks)
    fun verifyOtpWithCredential(credential: PhoneAuthCredential, onSuccess:(String)->Unit, onError:(errorMessage:String?)->Unit)
}
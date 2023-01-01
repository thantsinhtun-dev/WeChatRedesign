package com.stone.wechat.networks.auth

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

interface FirebaseAuthManager {
    fun sendVerificationCode(number:String,activity:AppCompatActivity,mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks)
    fun verifyOtpWithCredential(credential: PhoneAuthCredential, onSuccess:(String)->Unit, onError:(errorMessage:String?)->Unit)
    fun checkCurrentUser(onSuccess: (String) -> Unit, onError: (errorMessage: String?) -> Unit)
    fun createUserWithEmail(phone: String,password:String, onSuccess:(String)->Unit, onError:(errorMessage:String?)->Unit)
    fun loginWithEmail(phone: String,password: String,onSuccess:(String)->Unit, onError:(errorMessage:String?)->Unit)
    fun getCurrentUser(onSuccess: (String) -> Unit,onError: (errorMessage: String) -> Unit)

}
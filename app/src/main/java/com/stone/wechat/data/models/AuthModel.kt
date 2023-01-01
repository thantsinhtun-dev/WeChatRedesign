package com.stone.wechat.data.models

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl

interface AuthModel {
    var mAuthManager:FirebaseAuthManagerImpl
    var mCloudFireStoreApi:CloudFireStoreApi
    fun sendVerificationCode(number:String, activity: AppCompatActivity, mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks)
    fun verifyOtpWithCredential(credential: PhoneAuthCredential, onSuccess:(String)->Unit, onError:(errorMessage:String?)->Unit)
    fun checkCurrentUser(onSuccess: (String) -> Unit, onError: (errorMessage: String?) -> Unit)
    fun createUserWithEmail(phone: String,password:String, onSuccess:(String)->Unit, onError:(errorMessage:String?)->Unit)
    fun loginWithEmail(phone: String,password: String,onSuccess:(UserVO)->Unit, onError:(errorMessage:String?)->Unit)
    fun getCurrentUserFromFireStore(userId:String,onSuccess: (UserVO) -> Unit,onError: (errorMessage: String?) -> Unit)

}
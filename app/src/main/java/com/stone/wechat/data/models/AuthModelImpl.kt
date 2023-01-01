package com.stone.wechat.data.models

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl
import com.stone.wechat.networks.auth.FirebaseAuthManagerImpl

object AuthModelImpl :AuthModel{
    override var mAuthManager: FirebaseAuthManagerImpl =
        FirebaseAuthManagerImpl
    override var mCloudFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl

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
        onSuccess: (UserVO) -> Unit,
        onError: (errorMessage: String?) -> Unit
    ) {
        mAuthManager.loginWithEmail(phone, password, onSuccess= { userId ->
            Log.d("rx_read_user", userId)
            mCloudFireStoreApi.getCurrentUserFromFireStore(userId, onSuccess, onError)
        }, onError)
    }

    override fun getCurrentUserFromFireStore(
        userId: String,
        onSuccess: (UserVO) -> Unit,
        onError: (errorMessage: String?) -> Unit
    ) {
        mCloudFireStoreApi.getCurrentUserFromFireStore(userId, onSuccess, onError)
    }
}
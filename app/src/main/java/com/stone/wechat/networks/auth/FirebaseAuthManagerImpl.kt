package com.stone.wechat.networks.auth

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit


object FirebaseAuthManagerImpl : FirebaseAuthManager {

    private val mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun sendVerificationCode(
        number: String,
        activity: AppCompatActivity,
        mCallBack: OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(mFirebaseAuth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun verifyOtpWithCredential(
        credential: PhoneAuthCredential,
        onSuccess: (String) -> Unit,
        onError: (errorMessage: String?) -> Unit
    ) {
        mFirebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                try {
                    if (it.result.additionalUserInfo?.isNewUser == false) {
                        onError("User already exist")
                    } else {
                        if (it.isSuccessful && it.isComplete) {
                            Log.i("uid", it.result.user?.uid.toString())
                            onSuccess(it.result.user?.uid.toString() ?: "")

                        } else {
                            onError("OTP is incorrect")
                        }
                    }
                }catch (e:java.lang.Exception){
                    onError("OTP is incorrect")
                }
            }
            .addOnFailureListener {
                onError("OTP is incorrect")
            }
    }

    override fun checkCurrentUser(
        onSuccess: (String) -> Unit,
        onFailure: (errorMessage: String?) -> Unit
    ) {
        val user = mFirebaseAuth.currentUser
        if (user != null){
            onSuccess("exists")
        }else{
            onFailure("not exists")
        }
    }

    override fun createUserWithEmail(
        phone: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: (errorMessage: String?) -> Unit
    ) {
        mFirebaseAuth.signOut()
        mFirebaseAuth.createUserWithEmailAndPassword(phone.plus("@gmail.com"),password)
            .addOnCompleteListener {
                if (it.isSuccessful && it.isComplete) {
                    Log.i("uid", it.result.user?.uid.toString())
                    Log.i("user_info",it.result.user?.email + password)
                    onSuccess(it.result.user?.uid.toString() ?: "")

                }
            }.addOnFailureListener {
                onError(it.localizedMessage)
            }
    }

    override fun loginWithEmail(
        phone: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: (errorMessage: String?) -> Unit
    ) {
        Log.i("user_info",phone.plus("@gmail.com") + password)
        mFirebaseAuth.signInWithEmailAndPassword(phone.plus("@gmail.com"),password)
            .addOnCompleteListener {
                if (it.isSuccessful && it.isComplete) {
                    Log.i("uid", it.result.user?.uid.toString())
                    onSuccess(it.result.user?.uid.toString() ?: "")

                }
            }.addOnFailureListener {
                onError(it.localizedMessage)
            }
    }

    override fun getCurrentUser(
        onSuccess: (String) -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {
        val user = mFirebaseAuth.currentUser
        if (user != null){
            onSuccess(user.uid)
        }else{
            onFailure("user not exists")
        }

    }
//    private val mCallBack: OnVerificationStateChangedCallbacks =
//        object : OnVerificationStateChangedCallbacks() {
//            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
//                super.onCodeSent(s, forceResendingToken)
//                verificationId = s
//            }
//
//            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
//                val code = phoneAuthCredential.smsCode
//                if (code != null) {
//                    verifyCode(code)
//                }
//            }
//            override fun onVerificationFailed(e: FirebaseException) {
//                Log.i("otp",e.toString())
//            }
//        }
//    private fun verifyCode(code: String) {
//        val credential = PhoneAuthProvider.getCredential(verificationId, code)
//        signInWithCredential(credential)
//    }
//    private fun signInWithCredential(credential: PhoneAuthCredential) {
//        mFirebaseAuth.signInWithCredential(credential)
//            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
//                if (task.isSuccessful) {
//
//                } else {
//                }
//            })
//    }


}
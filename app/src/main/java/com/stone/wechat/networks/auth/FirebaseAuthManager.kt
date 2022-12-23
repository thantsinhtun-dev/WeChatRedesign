package com.stone.wechat.networks.auth

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit


object FirebaseAuthManager : AuthManager{

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
        onSuccess: () -> Unit,
        onError: (errorMessage: String?) -> Unit
    ) {
        mFirebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    onSuccess()
                }else {
                    onError(it.exception?.localizedMessage)
                }
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
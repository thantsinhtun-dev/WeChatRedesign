package com.stone.wechat.mvp.presenters

import DataStoreUtils.userDataStore
import DataStoreUtils.writeToRxDatastore
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.rxjava3.RxDataStore
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.models.AuthModel
import com.stone.wechat.data.models.AuthModelImpl
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.mvp.views.LoginView
import com.stone.wechat.networks.*

class LoginPresenterImpl : ViewModel(), LoginPresenter {
    private var mView: LoginView? = null
    private var phone: String = ""
    private var password: String = ""

    var dataStore: RxDataStore<Preferences>? = null


    //    private val mFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl
//    private var mAuthManager: AuthManager = FirebaseAuthManager
    private var mAuthModel:AuthModel = AuthModelImpl

    override fun initView(view: LoginView) {
        mView = view
    }

    override fun onChangePhoneNumber(phone: String) {
        this.phone = phone
        checkPhoneNumber()
    }

    override fun onChangePassword(password: String) {
        this.password = password
        checkPhoneNumber()
    }

    override fun onTapLogin() {
        mAuthModel.loginWithEmail(phone,password,
            onSuccess = {
                Log.d("rx_read", it.toString())

                saveUserToDatastore(it)
                mView?.navigateToMainActivity()
            },
            onError = {
                mView?.showErrorMessage("Error !",it ?: "Oops somethings")
            })

    }

    override fun onTapForgetPassword() {
    }

    override fun onUIReady(context: Context, lifecycleOwner: LifecycleOwner) {
        checkPhoneNumber()
        dataStore = context.userDataStore

    }

    override fun onUIReady(owner: LifecycleOwner) {

    }

    override fun onTapBackButton() {
        mView?.onTapBack()
    }

    private fun checkPhoneNumber() {
        mView?.activateLoginButton(phone.isNotEmpty() && password.isNotEmpty())
    }

    private fun saveUserToDatastore(
       userVO: UserVO
    ) {
        dataStore?.writeToRxDatastore(FIRE_STORE_USER_VO_USERID, userVO.userId ?: "jkjkjk")
        dataStore?.writeToRxDatastore(FIRE_STORE_USER_VO_NAME, userVO.name ?:"")
        dataStore?.writeToRxDatastore(FIRE_STORE_USER_VO_PHONE, userVO.phone ?: "")
        dataStore?.writeToRxDatastore(FIRE_STORE_USER_VO_DOB, userVO.dob?:"")
        dataStore?.writeToRxDatastore(FIRE_STORE_USER_VO_GENDER, userVO.gender?:"")
        dataStore?.writeToRxDatastore(FIRE_STORE_USER_VO_PROFILE, userVO.profile?:"")
        dataStore?.writeToRxDatastore(FIRE_STORE_USER_VO_QRCODE, userVO.qrCode?:"")

    }
}
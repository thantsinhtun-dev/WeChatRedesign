package com.stone.wechat.mvp.presenters

import DataStoreUtils.userDataStore
import DataStoreUtils.writeToRxDatastore
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.rxjava3.RxDataStore
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.mvp.views.SignUpView
import com.stone.wechat.networks.*
import com.stone.wechat.networks.auth.AuthManager
import com.stone.wechat.networks.auth.FirebaseAuthManager

class SignUpPresenterImpl : ViewModel(), SignUpPresenter {

    private var mView: SignUpView? = null
    private var mFireBaseApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl
    private var mAuthManager: AuthManager = FirebaseAuthManager

    var dataStore: RxDataStore<Preferences>? = null



    private var selectedDay: String = ""
    private var selectedMonth: String = ""
    private var selectedYear: String = ""
    private var selectedGender: String = "Male"
    private var name: String = ""
    private var password: String = ""
    private var isCheckedAgree: Boolean = false

    override fun initView(view: SignUpView) {
        mView = view
    }

    override fun onChangeName(text: String) {
        name = text
        checkValidUserInput()

    }

    override fun onChangePassword(text: String) {

        password = text

        checkValidUserInput()
    }

    override fun onChangeGender(text: String) {
        if (text.isNotEmpty()) {
            selectedGender = text
        }
        checkValidUserInput()
    }

    override fun onChangeCheckBox(isChecked: Boolean) {
        isCheckedAgree = isChecked
        checkValidUserInput()
    }

    override fun onSelectDay(day: String) {
        if (day.isNotEmpty()) {
            selectedDay = day
        }
        checkValidUserInput()
    }

    override fun onSelectMonth(month: String) {
        if (month.isNotEmpty()) {
            selectedMonth = month
        }
        checkValidUserInput()
    }

    override fun onSelectYear(year: String) {
        if (year.isNotEmpty()) {
            selectedYear = year
        }
        checkValidUserInput()
    }

    override fun onTapSignUp(phone: String, userId: String) {
//        mView?.navigateToVerification(name,selectedDay.plus("/").plus(selectedMonth).plus("/").plus(selectedYear),selectedGender,password)
        mAuthManager.createUserWithEmail(phone, password,
            onSuccess = {
                saveUserToDatastore(it)
                mFireBaseApi.createUser(
                    it,
                    name,
                    phone,
                    selectedDay.plus("/").plus(selectedMonth).plus("/").plus(selectedYear),
                    selectedGender,
                    password,
                    onSuccess = {
                        mView?.navigateToMainActivity()
                    },
                    onFailure = {
                        mView?.showError(it)
                    }
                )
            },
            onError = {
                mView?.showError(it ?: "error")
            })


    }

    override fun onTapBack() {
    }

    override fun onUIReady(context: Context, owner: LifecycleOwner) {
        dataStore = context.userDataStore

    }

    override fun onUIReady(owner: LifecycleOwner) {
        TODO("Not yet implemented")
    }

    override fun onTapBackButton() {
        mView?.onTapBack()
    }


    private fun checkValidUserInput() {
        if (name.isNotEmpty() && password.isNotEmpty() && selectedGender.isNotEmpty() && selectedDay.isNotEmpty() && selectedMonth.isNotEmpty() && selectedYear.isNotEmpty() && isCheckedAgree) {
            mView?.activateSignUpButton(true)
        } else mView?.activateSignUpButton(false)
    }
    private fun saveUserToDatastore(
        userId: String
    ) {
        dataStore?.writeToRxDatastore(FIRE_STORE_USER_VO_USERID, userId)
    }
}
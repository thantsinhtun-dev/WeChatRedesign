package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.mvp.views.SignUpView
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl

class SignUpPresenterImpl : ViewModel(), SignUpPresenter {

    private var mView: SignUpView? = null
    private var mFireBaseApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl


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
        mFireBaseApi.createUser(
            userId,
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
    }

    override fun onTapBack() {
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
}
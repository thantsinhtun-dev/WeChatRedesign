package com.stone.wechat.mvp.presenters

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.stone.wechat.mvp.views.SignUpView

interface SignUpPresenter :BasePresenter{
    fun onUIReady(context: Context,owner: LifecycleOwner)
    fun initView(view: SignUpView)
    fun onChangeName(text:String)
    fun onChangePassword(text: String)
    fun onChangeGender(text: String)
    fun onChangeCheckBox(isChecked:Boolean)
    fun onSelectDay(day:String)
    fun onSelectMonth(month:String)
    fun onSelectYear(year:String)
    fun onTapSignUp(phone: String, userId: String)
    fun onTapBack()
}
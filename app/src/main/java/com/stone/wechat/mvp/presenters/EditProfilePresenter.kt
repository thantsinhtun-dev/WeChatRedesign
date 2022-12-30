package com.stone.wechat.mvp.presenters

import com.stone.wechat.mvp.views.EditProfileView

interface EditProfilePresenter:BasePresenter {
    fun initView(view:EditProfileView)
    fun onTapDismiss()
    fun onTapSave(userName:String,phone:String,dob:String,gender:String,password:String,profile:String,qr:String,userId:String)
}
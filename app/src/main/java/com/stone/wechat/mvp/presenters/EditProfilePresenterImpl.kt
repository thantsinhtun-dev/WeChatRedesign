package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.models.ProfileModel
import com.stone.wechat.data.models.ProfileModelImpl
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.mvp.views.EditProfileView

class EditProfilePresenterImpl:ViewModel(),EditProfilePresenter {
    private var mView:EditProfileView? = null
    private val mProfileModel: ProfileModel = ProfileModelImpl

    override fun initView(view: EditProfileView) {
        mView = view
    }

    override fun onTapDismiss() {
        mView?.cancelDialog()
    }

    override fun onTapSave(
        userName: String,
        phone: String,
        dob: String,
        gender: String,
        password: String,
        profile: String,
        qr: String,
        userId: String
    ) {
        if (userName.isNotEmpty() && phone.isNotEmpty() && dob.isNotEmpty() && gender.isNotEmpty()){
            mProfileModel.updateProfileData(
                userVO = UserVO(userName,phone, dob, gender,password, userId,qr,profile),
                onSuccess = {
                     mView?.navigateBack()
                },
                onFailure = {
                    mView?.showError(it)
                }
            )
        }
    }

    override fun onUIReady(owner: LifecycleOwner) {
        TODO("Not yet implemented")
    }

    override fun onTapBackButton() {
        TODO("Not yet implemented")
    }
}
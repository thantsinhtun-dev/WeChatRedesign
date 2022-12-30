package com.stone.wechat.mvp.presenters

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.rxjava3.RxDataStore
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.models.AuthModel
import com.stone.wechat.data.models.AuthModelImpl
import com.stone.wechat.data.models.ProfileModel
import com.stone.wechat.data.models.ProfileModelImpl
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.mvp.views.ProfileView
import com.stone.wechat.networks.*
import com.stone.wechat.utils.DataStoreUtils.readQuick
import com.stone.wechat.utils.DataStoreUtils.userDataStore
import org.checkerframework.checker.units.qual.m

class ProfilePresenterImpl : ViewModel(), ProfilePresenter {
    private var mView: ProfileView? = null
    private var dataStore: RxDataStore<Preferences>? = null
    private var userVO: UserVO? = null


    private val mProfileModel: ProfileModel = ProfileModelImpl

    override fun initView(view: ProfileView) {
        mView = view
    }

    override fun onTapEditUserInfo() {
        userVO?.let {
            mView?.showEditUserInfoDialog(it)
        }
    }

    override fun onTapUploadProfile() {
        mView?.pickProfileImage()
    }

    override fun selectedContent(bitmap: Bitmap) {
        userVO?.let {
            mProfileModel.updateProfileImage(
                bitmap,
                it,
                onSuccess = {

                },
                onFailure = { error->
                    mView?.showError(error)
                })
        }
    }

    override fun onTapQrCode() {
        mView?.showQrCode()
    }

    override fun onUIReady(context: Context, owner: LifecycleOwner) {
        dataStore = context.userDataStore
        getUserDataFromStore()
    }

    override fun onUIReady(owner: LifecycleOwner) {

    }

    override fun onTapBackButton() {

    }

    private fun getUserDataFromStore() {


        dataStore?.readQuick(FIRE_STORE_USER_VO_USERID) {
            Log.d("rx_read", it)
            mProfileModel.getProfileData(
                it,
                onSuccess = { user ->
                    this.userVO = user
                    mView?.initProfile(user)
                },
                onFailure = { error ->
                    mView?.showError(error)
                }
            )
        }


    }

}
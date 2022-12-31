package com.stone.wechat.mvp.presenters

import DataStoreUtils.readQuick
import DataStoreUtils.userDataStore
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.rxjava3.RxDataStore
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.models.AuthModel
import com.stone.wechat.data.models.AuthModelImpl
import com.stone.wechat.data.models.ProfileModel
import com.stone.wechat.data.models.ProfileModelImpl
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.mvp.views.CreateMomentView
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl
import com.stone.wechat.networks.FIRE_STORE_USER_VO_USERID

class CreateMomentPresenterImpl : ViewModel(), CreateMomentPresenter {
    private var mView: CreateMomentView? = null

    private val mFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl
    private val mProfileModel: ProfileModel = ProfileModelImpl
    private val mAuthModel:AuthModel = AuthModelImpl
    var dataStore: RxDataStore<Preferences>? = null


    private var userVO:UserVO? = null

    private var selectedContents: List<MomentFileVO> = arrayListOf()
    override fun initView(view: CreateMomentView) {
        mView = view
    }

    override fun onTapBack() {
        mView?.navigateBack()
    }

    override fun onTapCreateMoment(momentText: String) {
        userVO?.let {
            mFireStoreApi.createMoment(it,momentText, selectedContents,
                onSuccess = {
                    mView?.navigateBack()
                },
                onFailure = {
                    mView?.showError(it)
                })
        }
    }

    override fun onTapUploadContent() {
        mView?.openGallery()
    }

    override fun selectedContent(selectedContents: List<MomentFileVO>) {
        this.selectedContents = selectedContents
        mView?.showSelectedContents(this.selectedContents)
    }

    override fun onUIReady(context: Context, owner: LifecycleOwner) {
        dataStore = context.userDataStore
        dataStore?.readQuick(FIRE_STORE_USER_VO_USERID) {
            mAuthModel.getCurrentUserFromFireStore(it,
                onSuccess = { user ->
                    this.userVO = user
                    mView?.initProfile(user)
                },
                onError = { error->
                    mView?.showError(error ?: "error")
                }
            )
        }

    }


    override fun onUIReady(owner: LifecycleOwner) {
    }

    override fun onTapBackButton() {
        mView?.onTapBack()
    }
}
package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.mvp.views.CreateMomentView
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl

class CreateMomentPresenterImpl : ViewModel(), CreateMomentPresenter {
    private var mView: CreateMomentView? = null

    private val mFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl

    private var selectedContents: List<MomentFileVO> = arrayListOf()
    override fun initView(view: CreateMomentView) {
        mView = view
    }

    override fun onTapBack() {
        mView?.navigateBack()
    }

    override fun onTapCreateMoment(momentText: String) {
        mFireStoreApi.createMoment(momentText, selectedContents,
            onSuccess = {
                        mView?.navigateBack()
            },
            onFailure = {
                mView?.showError(it)
            })
    }

    override fun onTapUploadContent() {
        mView?.openGallery()
    }

    override fun selectedContent(selectedContents: List<MomentFileVO>) {
        this.selectedContents = selectedContents
        mView?.showSelectedContents(this.selectedContents)
    }


    override fun onUIReady(owner: LifecycleOwner) {
    }

    override fun onTapBackButton() {
        mView?.onTapBack()
    }
}
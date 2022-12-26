package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.mvp.views.MomentView
import com.stone.wechat.networks.CloudFireStoreApi
import com.stone.wechat.networks.CloudFireStoreFirebaseApiImpl

class MomentPresenterImpl:ViewModel(),MomentPresenter {
    private var mView:MomentView? = null
    private val mFireStoreApi: CloudFireStoreApi = CloudFireStoreFirebaseApiImpl
    override fun initView(view: MomentView) {
        mView = view
    }

    override fun onTapCreatePost() {
        mView?.navigateToCreateMoment()
    }

    override fun onTapLike() {
        TODO("Not yet implemented")
    }

    override fun onTapComment() {
        TODO("Not yet implemented")
    }

    override fun onTapBookMark() {
        TODO("Not yet implemented")
    }

    override fun onTapImage() {
        TODO("Not yet implemented")
    }

    override fun onUIReady(owner: LifecycleOwner) {
        mFireStoreApi.getMoments(
            onSuccess = {
                mView?.initMoment(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )

    }

    override fun onTapBackButton() {
        TODO("Not yet implemented")
    }
}
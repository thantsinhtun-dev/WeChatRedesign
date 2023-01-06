package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.stone.wechat.delegates.MomentDelegate
import com.stone.wechat.mvp.views.MomentView

interface MomentPresenter:BasePresenter,MomentDelegate {
    fun onUIReady(owner: LifecycleOwner)
    fun initView(view:MomentView)
    fun onTapCreatePost()

}
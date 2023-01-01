package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.stone.wechat.mvp.views.MomentView

interface MomentPresenter:BasePresenter {
    fun onUIReady(owner: LifecycleOwner)
    fun initView(view:MomentView)
    fun onTapCreatePost()
    fun onTapLike()
    fun onTapComment()
    fun onTapBookMark()
    fun onTapImage()
}
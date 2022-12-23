package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner

interface BasePresenter {
    fun onUIReady(owner: LifecycleOwner)
    fun onTapBackButton()
}
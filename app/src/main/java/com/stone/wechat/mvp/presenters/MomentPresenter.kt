package com.stone.wechat.mvp.presenters

import com.stone.wechat.mvp.views.MomentView

interface MomentPresenter:BasePresenter {
    fun initView(view:MomentView)
    fun onTapCreatePost()
    fun onTapLike()
    fun onTapComment()
    fun onTapBookMark()
    fun onTapImage()
}
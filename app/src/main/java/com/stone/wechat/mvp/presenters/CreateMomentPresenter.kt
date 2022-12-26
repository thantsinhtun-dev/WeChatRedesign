package com.stone.wechat.mvp.presenters

import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.mvp.views.CreateMomentView

interface CreateMomentPresenter :BasePresenter{
    fun initView(view:CreateMomentView)
    fun onTapBack()
    fun onTapCreateMoment(momentText:String)
    fun onTapUploadContent()
    fun selectedContent(selectedContents:List<MomentFileVO>)
}
package com.stone.wechat.mvp.views

import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.data.vos.UserVO

interface CreateMomentView:BaseView {
    fun initProfile(userVO: UserVO)
    fun openGallery()
    fun navigateBack()
    fun showSelectedContents(selectedContents:List<MomentFileVO>)

}
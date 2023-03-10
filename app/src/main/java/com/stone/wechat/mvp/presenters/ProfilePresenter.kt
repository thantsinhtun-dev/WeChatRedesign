package com.stone.wechat.mvp.presenters

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.stone.wechat.delegates.MomentDelegate
import com.stone.wechat.mvp.views.ProfileView

interface ProfilePresenter:BasePresenter, MomentDelegate {
    fun onUIReady(owner: LifecycleOwner)
    fun onUIReady(context: Context,owner: LifecycleOwner)
    fun initView(view:ProfileView)
    fun onTapEditUserInfo()
    fun onTapUploadProfile()
    fun selectedContent(bitmap: Bitmap)
    fun onTapQrCode()
}
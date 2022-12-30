package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.GroupVO
import com.stone.wechat.mvp.views.ContactView

class ContactPresenterImpl : ViewModel(),ContactPresenter {
    private  var mView:ContactView? = null

    override fun initView(view: ContactView) {
        mView = view
    }

    override fun onTapNewContact() {
        mView?.navigateToQrScanner()
    }

    override fun onTapNewGroup() {
        mView?.navigateToAddNewGroup()
    }

    override fun onTapGroupItem(groupVO: GroupVO) {
        TODO("Not yet implemented")
    }

    override fun onTapContactItem(contactVO: ContactVO) {
        TODO("Not yet implemented")
    }

    override fun onSearchItem(string: String) {
        TODO("Not yet implemented")
    }

    override fun onUIReady(owner: LifecycleOwner) {
        mView?.initContacts(arrayListOf())
        mView?.initGroups(arrayListOf())
    }

    override fun onTapBackButton() {

    }
}
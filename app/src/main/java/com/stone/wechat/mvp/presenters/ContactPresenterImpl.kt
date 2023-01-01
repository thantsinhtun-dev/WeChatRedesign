package com.stone.wechat.mvp.presenters

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.models.ContactModel
import com.stone.wechat.data.models.ContactModelImpl
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.GroupVO
import com.stone.wechat.mvp.views.ContactView
import com.stone.wechat.utils.dummyContacts

class ContactPresenterImpl : ViewModel(), ContactPresenter {
    private var mView: ContactView? = null

    private val mContactModel: ContactModel = ContactModelImpl

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

    override fun addContact(contactUserId: String) {
        mContactModel.addContacts(contactUserId, onSuccess = {
                                                             mView?.hideLoading()
        }, onFailure = {
            mView?.showError(it)
        })
    }

    override fun onSearchItem(string: String) {
        TODO("Not yet implemented")
    }

    override fun onUIReady(owner: LifecycleOwner) {
        mContactModel.getAllContacts(
            onSuccess = {
                Log.i("contact_count",it.count().toString())

                mView?.initContacts(it)
            }, onFailure = {
                mView?.showError(it)
            })
        mView?.initGroups(arrayListOf())
    }

    override fun onTapBackButton() {

    }
}
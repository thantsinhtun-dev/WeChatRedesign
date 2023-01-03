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

    override fun addContact(contactUserId: String) {
        mContactModel.addContacts(contactUserId, onSuccess = {
                                                             mView?.hideLoading()
        }, onFailure = {
            mView?.showError(it)
        })
    }
    override fun onTapGroup(groupVO: GroupVO) {
        val contactVO = ContactVO(groupVO.groupId,groupVO.groupName,groupVO.groupPhoto,"true")
        mView?.navigateToChatDetails(contactVO,isGroup = true)
    }

    override fun onSearchItem(string: String) {

    }

    override fun onUIReady(owner: LifecycleOwner) {
        mContactModel.getAllContacts(
            onSuccess = {
                Log.i("contact_count",it.count().toString())

                mView?.initContacts(it)
            }, onFailure = {
                mView?.showError(it)
            })
        mContactModel.getAllGroups(
            onSuccess = {
                mView?.initGroups(it)
            }, onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapBackButton() {

    }

    override fun onTapContact(contactVO: ContactVO) {
        mView?.navigateToChatDetails(contactVO,isGroup = false)
    }

    override fun onTapCheckBox(contactVO: ContactVO) {
    }


}
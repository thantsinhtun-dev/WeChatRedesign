package com.stone.wechat.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.stone.wechat.data.models.AuthModel
import com.stone.wechat.data.models.AuthModelImpl
import com.stone.wechat.data.models.ContactModel
import com.stone.wechat.data.models.ContactModelImpl
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.mvp.views.CreateNewGroupView

class CreateNewGroupPresenterImpl : ViewModel(), CreateNewGroupPresenter {

    private var mView: CreateNewGroupView? = null

    private var mContactModel: ContactModel = ContactModelImpl


    private var mContactList: MutableList<ContactVO> = mutableListOf()
    private var mSelectedContacts: MutableList<ContactVO> = mutableListOf()


    private var groupPhotoUrl = ""

    override fun initView(view: CreateNewGroupView) {
        mView = view
    }

    override fun onUIReady(owner: LifecycleOwner) {
        mContactModel.getAllContacts(
            onSuccess = {
                this.mContactList.addAll(it)
                mView?.showAllContacts(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapContact(contactVO: ContactVO) {
    }

    override fun onTapCheckBox(contactVO: ContactVO) {
        if (contactVO.isSelected) {
            mSelectedContacts.add(contactVO)
        } else {
            mSelectedContacts.remove(contactVO)
        }
        mView?.showSelectedContact(mSelectedContacts)
    }

    override fun onTapRemoveContact(contactVO: ContactVO) {
        mSelectedContacts.remove(contactVO)
        mView?.showSelectedContact(mSelectedContacts)

        mContactList.forEach {
            if (it.contactId == contactVO.contactId) {
                it.isSelected = false
            }
        }

        mView?.showAllContacts(mContactList)
    }

    override fun uploadGroupPhoto() {

    }

    override fun onChangeGroupName(groupName: String) {

    }

    override fun onTapClearSearch() {
    }

    override fun onSearchContact(query: String) {
        if (query.isNotEmpty()) {
            mView?.showAllContacts(
                mContactList.filter {
                    it.contactName.uppercase().contains(query.uppercase())
                })
        } else {
            mView?.showAllContacts(mContactList)
        }
    }

    override fun onTapCreate(groupName: String) {

        mContactModel.createGroup(
            groupName = groupName,
            groupPhoto = groupPhotoUrl,
            memberList = mSelectedContacts.map { it.contactId },
            onSuccess = {
                mView?.onTapBack()
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapBackButton() {
        mView?.onTapBack()
    }
}
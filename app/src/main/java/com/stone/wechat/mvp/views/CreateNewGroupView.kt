package com.stone.wechat.mvp.views

import com.stone.wechat.data.vos.ContactVO

interface CreateNewGroupView :BaseView{
    fun showSelectedUserList(contactsList: List<ContactVO>)
    fun showAllContacts(contactsList: List<ContactVO>)
    fun showSearchResult(contactsList: List<ContactVO>)
    fun showSelectedContact(contactsList: List<ContactVO>)
}
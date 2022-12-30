package com.stone.wechat.mvp.views

import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.GroupVO

interface ContactView {
    fun initGroups(groups:List<GroupVO>)
    fun initContacts(contacts:List<ContactVO>)
    fun navigateToChat()
    fun navigateToAddNewGroup()
    fun navigateToQrScanner()

}
package com.stone.wechat.delegates

import com.stone.wechat.data.vos.ContactVO

interface SelectedContactDelegate {
    fun onTapRemoveContact(contactVO: ContactVO)
}
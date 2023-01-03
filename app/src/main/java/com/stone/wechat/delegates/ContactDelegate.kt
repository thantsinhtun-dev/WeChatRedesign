package com.stone.wechat.delegates

import com.stone.wechat.data.vos.ContactVO

interface ContactDelegate {
    fun onTapContact(contactVO: ContactVO)
    fun onTapCheckBox(contactVO: ContactVO)
}
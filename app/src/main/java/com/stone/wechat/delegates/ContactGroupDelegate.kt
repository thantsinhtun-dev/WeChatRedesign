package com.stone.wechat.delegates

import com.stone.wechat.data.vos.GroupVO

interface ContactGroupDelegate {
    fun onTapGroup(groupVO: GroupVO)
}
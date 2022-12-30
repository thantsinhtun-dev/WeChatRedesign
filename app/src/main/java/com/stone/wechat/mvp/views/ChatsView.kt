package com.stone.wechat.mvp.views

import com.stone.wechat.data.vos.ChatHistoryVO
import com.stone.wechat.data.vos.UserVO

interface ChatsView : BaseView {
    fun initChats(chats: List<ChatHistoryVO>)
    fun initActiveUsers(activeUser: List<UserVO>)
    fun navigateToSearchActivity()
    fun navigateToChatActivity(selectedUserVO: UserVO)
}
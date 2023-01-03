package com.stone.wechat.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.adapter.ActiveNowAdapter
import com.stone.wechat.adapter.ChatHistoryAdapter
import com.stone.wechat.data.vos.ChatHistoryVO
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.mvp.presenters.ChatsPresenter
import com.stone.wechat.mvp.presenters.ChatsPresenterImpl
import com.stone.wechat.mvp.views.ChatsView
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : BaseFragment(),ChatsView {

    private lateinit var mPresenter:ChatsPresenter
    private lateinit var mActiveUserAdapter:ActiveNowAdapter
    private lateinit var mChatHistoryAdapter: ChatHistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpPresenter()
        setUpListener()
        setUpRecyclerView()

        mPresenter.onUIReady(this)
    }



    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ChatsPresenterImpl::class.java]
        mPresenter.initView(this)
    }
    private fun setUpListener() {
    }
    private fun setUpRecyclerView() {
        mActiveUserAdapter = ActiveNowAdapter()
        mChatHistoryAdapter = ChatHistoryAdapter()

        rvActiveUser.adapter = mActiveUserAdapter
        rvChatHistory.adapter = mChatHistoryAdapter
    }
    override fun initChats(chats: List<ChatHistoryVO>) {
        mChatHistoryAdapter.setNewData(chats)
    }

    override fun initActiveUsers(activeUser: List<UserVO>) {
        mActiveUserAdapter.setNewData(activeUser)
    }

    override fun navigateToSearchActivity() {
        TODO("Not yet implemented")
    }

    override fun navigateToChatActivity(selectedUserVO: UserVO) {
        TODO("Not yet implemented")
    }

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun onTapBack() {
        TODO("Not yet implemented")
    }


}
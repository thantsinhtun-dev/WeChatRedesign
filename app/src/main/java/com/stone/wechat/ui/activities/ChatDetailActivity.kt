package com.stone.wechat.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.adapter.MessageAdapter
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.MessagesVO
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.mvp.presenters.ChatsDetailsPresenter
import com.stone.wechat.mvp.presenters.ChatsDetailsPresenterImpl
import com.stone.wechat.mvp.views.ChatDetailsView
import kotlinx.android.synthetic.main.activity_chat_detail.*

class ChatDetailActivity : BaseActivity(),ChatDetailsView {
    override val layoutId: Int = R.layout.activity_chat_detail

    private lateinit var mPresenter:ChatsDetailsPresenter
    private lateinit var mMessageAdapter: MessageAdapter
    companion object{
        private const val EXTRA_CONTACT = "contact"
        private const val EXTRA_IS_GROUP = "is_group"

        fun getIntent(context: Context, contactVO: ContactVO, isGroup: Boolean): Intent {
            val intent = Intent(context,ChatDetailActivity::class.java)
            intent.putExtra(EXTRA_CONTACT,contactVO)
            intent.putExtra(EXTRA_IS_GROUP,isGroup)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val contactVO = intent.getSerializableExtra(EXTRA_CONTACT) as ContactVO
        val isGroup = intent.getBooleanExtra(EXTRA_IS_GROUP,false)

        setUpPresenter()
        setUpListener()
        setUpRecyclerView()

        mPresenter.onUiReady(contactVO,isGroup,this)


    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ChatsDetailsPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun setUpListener() {
        imgSend.setOnClickListener {
           val message = editMessage.text.toString()
            if (message.isNotEmpty()){
                mPresenter.onTapSend(message)
                editMessage.text?.clear()
            }
        }
    }

    private fun setUpRecyclerView() {
        mMessageAdapter = MessageAdapter()
        rvChatMessage.adapter = mMessageAdapter
    }

    override fun initChats(messages: List<MessagesVO>,currentUserId:String) {
        mMessageAdapter.setNewData(messages,currentUserId)
        rvChatMessage.smoothScrollToPosition(messages.size)
    }

    override fun showFailureMessages(messages: String) {
        TODO("Not yet implemented")
    }

    override fun showGallery() {
        TODO("Not yet implemented")
    }

    override fun showSelectedContents(selectedContents: List<MomentFileVO>) {
        TODO("Not yet implemented")
    }

    override fun initContact(contactVO: ContactVO) {
        lblUserName.text = contactVO.contactName
    }

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun onTapBack() {
        onBackPressed()
    }
}
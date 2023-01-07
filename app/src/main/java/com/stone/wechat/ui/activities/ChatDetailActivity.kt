package com.stone.wechat.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.snov.timeagolibrary.PrettyTimeAgo
import com.stone.wechat.R
import com.stone.wechat.adapter.MessageAdapter
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.MessagesVO
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.mvp.presenters.ChatsDetailsPresenter
import com.stone.wechat.mvp.presenters.ChatsDetailsPresenterImpl
import com.stone.wechat.mvp.views.ChatDetailsView
import com.stone.wechat.utils.getTimeAgo
import com.stone.wechat.utils.loadBitmapFromUri
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chat_detail.*
import java.io.IOException

class ChatDetailActivity : BaseActivity(), ChatDetailsView {
    override val layoutId: Int = R.layout.activity_chat_detail

    private lateinit var mPresenter: ChatsDetailsPresenter
    private lateinit var mMessageAdapter: MessageAdapter

    companion object {
        private const val EXTRA_CONTACT = "contact"
        private const val EXTRA_IS_GROUP = "is_group"

        fun getIntent(context: Context, contactVO: ContactVO, isGroup: Boolean): Intent {
            val intent = Intent(context, ChatDetailActivity::class.java)
            intent.putExtra(EXTRA_CONTACT, contactVO)
            intent.putExtra(EXTRA_IS_GROUP, isGroup)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val contactVO = intent.getSerializableExtra(EXTRA_CONTACT) as ContactVO
        val isGroup = intent.getBooleanExtra(EXTRA_IS_GROUP, false)
        if (isGroup){
            lblOnlineStatus.visibility = View.GONE
        }

        setUpPresenter()
        setUpListener()
        setUpRecyclerView()

        mPresenter.onUiReady(contactVO, isGroup, this)


    }

    private var launchContentPicker = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            if (data != null) {
                val clipPath = data.clipData
                if (clipPath != null) {
                    val imageUriList = arrayListOf<Uri>()
                    val imageList: ArrayList<MomentFileVO> = arrayListOf()
                    for (i in 0 until clipPath.itemCount) {
                        if (clipPath.getItemAt(i).uri.toString().contains("video")) {
                            continue
                        } else if (clipPath.getItemAt(i).uri.toString().contains("image")) {
                            imageUriList.add(clipPath.getItemAt(i).uri)
                        }
                    }
                    Observable.just(imageUriList)
                        .map {
                            imageUriList.map {
                                it.loadBitmapFromUri(this)
                            }
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            it.forEach { bitmap ->
                                val vo = MomentFileVO("", bitmap, false, null)
                                imageList.add(vo)
                            }
                            mPresenter.selectedContent(imageList)
                        }
                } else {
                    val filePath = data.data
                    try {
                        filePath?.let {
                            if (it.toString().contains("video") ||
                                it.toString().contains("image")
                            ) {
                                Observable.just(it)
                                    .map { uri ->
                                        val isMovie = uri.toString().contains("video")
                                        MomentFileVO("", uri.loadBitmapFromUri(this), isMovie, uri)
                                    }
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe { vo ->
                                        mPresenter.selectedContent(arrayListOf(vo))
                                    }
                            }
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ChatsDetailsPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun setUpListener() {
        imgSend.setOnClickListener {
            val message = editMessage.text.toString()
            if (message.isNotEmpty()) {
                mPresenter.onTapSend(message)
                editMessage.text?.clear()
            }
        }
        imgChoosePhoto.setOnClickListener {
            mPresenter.onChooseImage()
        }
        imgBack.setOnClickListener {
            mPresenter.onTapBackButton()
        }

    }

    private fun setUpRecyclerView() {
        mMessageAdapter = MessageAdapter()
        rvChatMessage.adapter = mMessageAdapter
    }

    override fun initChats(messages: List<MessagesVO>, currentUserId: String) {
        mMessageAdapter.setNewData(messages, currentUserId)
        rvChatMessage.smoothScrollToPosition(messages.size)
    }

    override fun showFailureMessages(messages: String) {
    }

    override fun showGallery(isMovie: Boolean) {

        val intent = Intent(Intent.ACTION_PICK)


        intent.type = "*/*"

        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        launchContentPicker.launch(intent)
    }

    override fun showSelectedContents(selectedContents: List<MomentFileVO>) {
    }

    override fun initContact(contactVO: ContactVO) {
        Log.i("Gooo",contactVO.toString())
        lblUserName.text = contactVO.contactName
        Glide.with(this)
            .load(contactVO.contactImage)
            .placeholder(R.drawable.ic_avator)
            .into(imgChatIcon)
        if(contactVO.onlineStatus ){
            lblOnlineStatus.text = "online"
            imgOnlineStatusIcon.setImageResource(R.drawable.ic_active)
        }else{
            lblOnlineStatus.text = contactVO.lastOnlineTime.getTimeAgo()
            imgOnlineStatusIcon.setImageResource(R.drawable.ic_inactive)
        }

    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun onTapBack() {
        onBackPressed()
    }
}
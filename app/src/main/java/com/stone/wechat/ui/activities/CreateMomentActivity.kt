package com.stone.wechat.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.adapter.MomentFileAdapter
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.mvp.presenters.CreateMomentPresenter
import com.stone.wechat.mvp.presenters.CreateMomentPresenterImpl
import com.stone.wechat.mvp.views.CreateMomentView
import com.stone.wechat.utils.loadBitmapFromUri
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_moment.*
import java.io.IOException


class CreateMomentActivity : BaseActivity(), CreateMomentView {
    private lateinit var mPresenter: CreateMomentPresenter
    private lateinit var mMomentFileAdapter: MomentFileAdapter

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CreateMomentActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_moment)

        setUpPresenter()
        setUpListener()
        setUpRecyclerView()

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
                                val vo = MomentFileVO("", bitmap,false,null)
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
        mPresenter = ViewModelProvider(this)[CreateMomentPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun setUpListener() {
        imgBack.setOnClickListener {
            mPresenter.onTapBack()
        }
        imgAddContent.setOnClickListener {
            mPresenter.onTapUploadContent()
        }
        btnCreateMoment.setOnClickListener {
            showLoadingView()
            mPresenter.onTapCreateMoment(momentText = editMoment.text.toString())
        }

    }

    private fun setUpRecyclerView() {
        mMomentFileAdapter = MomentFileAdapter()
        rvMomentFile.adapter = mMomentFileAdapter

    }

    override fun initProfile(userVO: UserVO) {
        TODO("Not yet implemented")
    }

    override fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        launchContentPicker.launch(intent)
    }

    override fun navigateBack() {
        hideLoadingView()
        onBackPressed()
    }

    override fun showSelectedContents(selectedContents: List<MomentFileVO>) {
        mMomentFileAdapter.setNewData(selectedContents)
    }

    override fun showError(error: String) {
        hideLoadingView()
        showFailureDialog("Error",error)
    }

    override fun onTapBack() {
        onBackPressed()
    }


}
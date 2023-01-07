package com.stone.wechat.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.adapter.ContactGroupAdapter
import com.stone.wechat.adapter.SelectedContactAdapter
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.MomentFileVO
import com.stone.wechat.mvp.presenters.CreateNewGroupPresenter
import com.stone.wechat.mvp.presenters.CreateNewGroupPresenterImpl
import com.stone.wechat.mvp.views.CreateNewGroupView
import com.stone.wechat.utils.loadBitmapFromUri
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_new_group.*
import java.io.IOException

class CreateNewGroupActivity : BaseActivity(),CreateNewGroupView {
    override val layoutId: Int = R.layout.activity_create_new_group

    private lateinit var mContactAdapter: ContactGroupAdapter
    private lateinit var mSelectedContactAdapter: SelectedContactAdapter
    private lateinit var mPresenter:CreateNewGroupPresenter
    companion object{
        fun getIntent(context: Context):Intent{
            return Intent(context,CreateNewGroupActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setUpPresenter()
        setUpListener()
        setUpRecyclerView()

        mPresenter.onUIReady(this)
    }

    private fun setUpListener() {
        imgBack.setOnClickListener { mPresenter.onTapBackButton() }
        btnCreateGroup.setOnClickListener { mPresenter.onTapCreate(editGroupName.text.toString()) }
        editSearch.addTextChangedListener {
            mPresenter.onSearchContact(it.toString())
        }

        imgDismiss.setOnClickListener {
            editSearch.text?.clear()
        }
        imgGroupPhoto.setOnClickListener{
            mPresenter.onTapGroupImage()
        }

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
                            mPresenter.selectedGroupImage(imageList[0])
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
                                        mPresenter.selectedGroupImage(vo)
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
    override fun  openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        launchContentPicker.launch(intent)
    }

    private fun setUpRecyclerView() {
        mContactAdapter = ContactGroupAdapter(mPresenter,isEditMode = true)
        mSelectedContactAdapter = SelectedContactAdapter(mPresenter)
        rvAllContacts.adapter = mContactAdapter
        rvSelectedContacts.adapter = mSelectedContactAdapter
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[CreateNewGroupPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    override fun showSelectedUserList(contactsList: List<ContactVO>) {
    }

    override fun showAllContacts(contactsList: List<ContactVO>) {
        if (contactsList.isEmpty()){
            rvAllContacts.visibility = View.GONE
        }else {
            rvAllContacts.visibility = View.VISIBLE
            mContactAdapter.setNewData(contactsList)
        }
    }

    override fun showSearchResult(contactsList: List<ContactVO>) {
    }

    override fun showSelectedContact(contactsList: List<ContactVO>) {
        if (contactsList.isEmpty()){
            rvSelectedContacts.visibility = View.GONE
        }else {
            rvSelectedContacts.visibility = View.VISIBLE
            mSelectedContactAdapter.setNewData(contactsList)
        }
    }

    override fun showGroupIcon(image: Bitmap) {
        imgGroupPhoto.setImageBitmap(image)
    }

    override fun showError(error: String) {
        showFailureDialog("Error",error)
    }

    override fun onTapBack() {
        onBackPressed()
    }
}
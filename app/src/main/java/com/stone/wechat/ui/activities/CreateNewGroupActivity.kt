package com.stone.wechat.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.adapter.ContactGroupAdapter
import com.stone.wechat.adapter.SelectedContactAdapter
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.mvp.presenters.CreateNewGroupPresenter
import com.stone.wechat.mvp.presenters.CreateNewGroupPresenterImpl
import com.stone.wechat.mvp.views.CreateNewGroupView
import kotlinx.android.synthetic.main.activity_create_new_group.*

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

    override fun showError(error: String) {
        showFailureDialog("Error",error)
    }

    override fun onTapBack() {
        onBackPressed()
    }
}
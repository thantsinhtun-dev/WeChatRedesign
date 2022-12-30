package com.stone.wechat.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.adapter.ContactAdapter
import com.stone.wechat.adapter.ContactGroupAdapter
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.GroupVO
import com.stone.wechat.mvp.presenters.ContactPresenter
import com.stone.wechat.mvp.presenters.ContactPresenterImpl
import com.stone.wechat.mvp.views.ContactView
import com.stone.wechat.ui.activities.AddNewContactActivity
import com.stone.wechat.ui.activities.CreateNewGroupActivity
import kotlinx.android.synthetic.main.fragment_contact.*


class ContactFragment : BaseFragment(),ContactView {

    private lateinit var mPresenter:ContactPresenter

    private lateinit var mContactAdapter: ContactAdapter
    private lateinit var mGroupAdapter: ContactGroupAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpPresenter()
        setUpListener()
        setUpRecyclerView()

        mPresenter.onUIReady(this)
    }

    private fun setUpRecyclerView() {
        mGroupAdapter = ContactGroupAdapter()
        mContactAdapter = ContactAdapter()

        rvGroup.adapter = mGroupAdapter
        rvContact.adapter = mContactAdapter
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ContactPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun setUpListener() {
        imgAddUser.setOnClickListener {
            mPresenter.onTapNewContact()
        }
        imgAddNewGroup.setOnClickListener {
            mPresenter.onTapNewGroup()
        }
    }

    override fun initGroups(groups: List<GroupVO>) {
        mGroupAdapter.setNewData(groups)
    }

    override fun initContacts(contacts: List<ContactVO>) {
        mContactAdapter.setNewData(contacts)
    }

    override fun navigateToChat() {
        TODO("Not yet implemented")
    }

    override fun navigateToAddNewGroup() {
        startActivity(CreateNewGroupActivity.getIntent(requireContext()))

    }

    override fun navigateToQrScanner() {
        startActivity(AddNewContactActivity.getIntent(requireContext()))
    }

}
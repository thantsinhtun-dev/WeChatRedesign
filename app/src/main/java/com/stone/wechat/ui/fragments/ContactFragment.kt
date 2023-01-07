package com.stone.wechat.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.adapter.ContactGroupAdapter
import com.stone.wechat.adapter.GroupAdapter
import com.stone.wechat.data.vos.ContactVO
import com.stone.wechat.data.vos.GroupVO
import com.stone.wechat.mvp.presenters.ContactPresenter
import com.stone.wechat.mvp.presenters.ContactPresenterImpl
import com.stone.wechat.mvp.views.ContactView
import com.stone.wechat.ui.activities.AddNewContactActivity
import com.stone.wechat.ui.activities.ChatDetailActivity
import com.stone.wechat.ui.activities.CreateNewGroupActivity
import com.stone.wechat.ui.activities.EXTRA_CONTACT_USER
import kotlinx.android.synthetic.main.fragment_contact.*
import java.util.jar.Manifest


class ContactFragment : BaseFragment(),ContactView {

    private lateinit var mPresenter:ContactPresenter

    private lateinit var mContactAdapter: ContactGroupAdapter
    private lateinit var mGroupAdapter: GroupAdapter

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

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val contactUserId = data?.getStringExtra(EXTRA_CONTACT_USER)
                Log.i("contactUserId",contactUserId.toString())
                if (contactUserId != null) {

                    showLoadingView()
                    mPresenter.addContact(contactUserId)
                }
            }else{
                Log.i("contactUserId","fail")

            }
        }
    private val requestCameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            mPresenter.onTapNewContact()
        }else{
            Toast.makeText(context, "Need to access camera for this feature", Toast.LENGTH_SHORT).show()
        }
    }



    private fun setUpRecyclerView() {
        mGroupAdapter = GroupAdapter(mPresenter)
        mContactAdapter = ContactGroupAdapter(mPresenter)

        rvGroup.adapter = mGroupAdapter
        rvContact.adapter = mContactAdapter
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(requireActivity())[ContactPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun setUpListener() {
        imgAddUser.setOnClickListener {
            requestCameraPermission.launch(android.Manifest.permission.CAMERA)
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

    override fun hideLoading() {
        hideLoadingView()
    }

    override fun navigateToAddNewGroup() {
        startActivity(CreateNewGroupActivity.getIntent(requireContext()))

    }

    override fun navigateToQrScanner() {
        resultLauncher.launch(AddNewContactActivity.getIntent(requireContext()))
    }

    override fun navigateToChatDetails(contactVO: ContactVO, isGroup: Boolean) {
        startActivity(ChatDetailActivity.getIntent(requireContext(),contactVO,isGroup))
    }

    override fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    override fun onTapBack() {
    }

}
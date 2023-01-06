package com.stone.wechat.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.adapter.MomentAdapter
import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.mvp.presenters.MomentPresenter
import com.stone.wechat.mvp.presenters.MomentPresenterImpl
import com.stone.wechat.mvp.views.MomentView
import com.stone.wechat.ui.activities.CreateMomentActivity
import kotlinx.android.synthetic.main.fragment_moment.*


class MomentFragment : BaseFragment(),MomentView {

    private lateinit var mPresenter: MomentPresenter
    private lateinit var mMomentAdapter:MomentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_moment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpListener()
        setUpRecyclerView()
        mPresenter.onUIReady(this)
    }



    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MomentPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun setUpListener() {
        btnCreatePost.setOnClickListener {
            mPresenter.onTapCreatePost()
        }
    }
    private fun setUpRecyclerView() {
        mMomentAdapter = MomentAdapter(mPresenter)
        rvMoment.adapter = mMomentAdapter
    }

    override fun initMoment(moments: List<MomentVO>) {
        mMomentAdapter.setNewData(moments)
    }

    override fun navigateToCreateMoment() {
        startActivity(CreateMomentActivity.getIntent(requireContext()))
    }

    override fun updateLikeCount(moments: List<MomentVO>, position: Int) {
        mMomentAdapter.updateData(moments,position)
    }

    override fun showError(error: String) {
        showFailureDialog("Error",error)
    }

    override fun onTapBack() {
        TODO("Not yet implemented")
    }


}
package com.stone.wechat.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.stone.wechat.R
import com.stone.wechat.adapter.MomentAdapter
import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.databinding.FragmentProfileBinding
import com.stone.wechat.dialogs.EditProfileDialog
import com.stone.wechat.dialogs.QRDialog
import com.stone.wechat.mvp.presenters.ProfilePresenter
import com.stone.wechat.mvp.presenters.ProfilePresenterImpl
import com.stone.wechat.mvp.views.ProfileView
import com.stone.wechat.utils.getQrCodeBitmap
import com.stone.wechat.utils.loadBitmapFromUri
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.IOException


class ProfileFragment : BaseFragment(), ProfileView {

    private lateinit var mPresenter: ProfilePresenter
    private lateinit var mMomentAdapter: MomentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpPresenter()
        setUpListener()
        setUpRecyclerView()
        mPresenter.onUIReady(requireContext(), this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ProfilePresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun setUpListener() {
        btnProfileEdit.setOnClickListener {
            mPresenter.onTapEditUserInfo()
        }
        imgUploadProfile.setOnClickListener {
            mPresenter.onTapUploadProfile()
        }
        imgQr.setOnClickListener {
            mPresenter.onTapQrCode()
        }
    }
    private fun setUpRecyclerView(){
        mMomentAdapter = MomentAdapter(mPresenter)
        rvBookMarkMoment.adapter = mMomentAdapter
    }

    private var launchContentPicker = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val data = result.data
            if (data != null) {

                val filePath = data.data
                try {
                    filePath?.let {

                        Observable.just(it)
                            .map { uri ->uri.loadBitmapFromUri(requireContext())
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { bitmap ->

                                mPresenter.selectedContent(bitmap)
                            }

                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    }


    override fun initProfile(userVO: UserVO) {
        lblUserName.text = userVO.name
        lblPhone.text = userVO.phone
        lblDob.text = userVO.dob
        lblGender.text = userVO.gender
        Glide.with(requireContext())
            .load(userVO.profile)
            .placeholder(R.drawable.ic_avator)
            .into(imgProfile)
        imgQr.setImageBitmap(userVO.qrCode?.getQrCodeBitmap())
    }

    override fun initBookMarkMoment(moments: List<MomentVO>) {
    }

    override fun showEditUserInfoDialog(userVO: UserVO) {
        EditProfileDialog(userVO).show(childFragmentManager.beginTransaction(), "")
    }

    override fun pickProfileImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        launchContentPicker.launch(intent)
    }

    override fun showQrCode(qr:String) {
        QRDialog(qr).show(childFragmentManager.beginTransaction(),"")
    }

    override fun changeProfileImage(url: String) {
        hideLoadingView()
        Glide.with(requireContext())
            .load(url).apply(

                RequestOptions().placeholder(R.drawable.progress_animation).centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .transform(CenterCrop(), RoundedCorners(1000))
            )
            .into(imgProfile)
    }

    override fun initMoment(moments: List<MomentVO>) {
        mMomentAdapter.setNewData(moments)
    }

    override fun updateLikeCount(moments: List<MomentVO>, position: Int) {
        mMomentAdapter.updateData(moments,position)
    }

    override fun showError(error: String) {
        hideLoadingView()
        showFailureDialog("Error", error)
    }

    override fun onTapBack() {
    }


}
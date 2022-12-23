package com.stone.wechat.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.mvp.presenters.VerificationPresenter
import com.stone.wechat.mvp.presenters.VerificationPresenterImpl
import com.stone.wechat.mvp.views.VerificationView
import kotlinx.android.synthetic.main.activity_verification.*

class VerificationActivity : AppCompatActivity(), VerificationView{

    private var name:String = ""
    private var password:String =""
    private var dob:String = ""
    private var gender:String = ""

    private lateinit var mPresenter: VerificationPresenter

    companion object{
        private const val EXTRA_NAME = "EXTRA_NAME"
        private const val EXTRA_PASSWORD = "EXTRA_PASSWORD"
        private const val EXTRA_DOB = "EXTRA_DOB"
        private const val EXTRA_GENDER = "EXTRA_GENDER"
        fun getIntent(context: Context,name:String,password:String,dob:String,gender:String):Intent{
            val intent = Intent(context,VerificationActivity::class.java)
            intent.putExtra(EXTRA_NAME,name)
            intent.putExtra(EXTRA_PASSWORD,password)
            intent.putExtra(EXTRA_DOB,dob)
            intent.putExtra(EXTRA_GENDER,gender)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        name = intent.getStringExtra(EXTRA_NAME).toString()
        password = intent.getStringExtra(EXTRA_PASSWORD).toString()
        dob = intent.getStringExtra(EXTRA_DOB).toString()
        gender = intent.getStringExtra(EXTRA_GENDER).toString()

        setUpPresenter()
        setUpEditText()
        setUpListener()

        mPresenter.onUIReady(this)



    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[VerificationPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun setUpListener() {
        btnGetOtp.setOnClickListener {
            mPresenter.onTapGetOtp(editPhone.toString())
        }
        btnVerify.setOnClickListener {
            mPresenter.onTapVerify(otp.toString())
        }
        imgBack.setOnClickListener { mPresenter.onTapBackButton() }
    }

    private fun setUpEditText() {
        editPhone.addTextChangedListener {
            mPresenter.onChangePhoneNumber(it.toString())
        }
        otp.addTextChangedListener {
            mPresenter.onChangeOtp(it.toString())
        }
    }

    override fun navigateToMainActivity() {
    }

    override fun activateOTPButton(activate: Boolean) {
        if(activate){
            btnGetOtp.isEnabled = true
            btnGetOtp.alpha = 1f
        }else{
            btnGetOtp.isEnabled = false
            btnGetOtp.alpha = 0.5f
        }
    }

    override fun activateVerifyButton(activate: Boolean) {
        if(activate){
            btnVerify.isEnabled = true
            btnVerify.alpha = 1f
        }else{
            btnVerify.isEnabled = false
            btnVerify.alpha = 0.5f
        }
    }

    override fun showError(error: String) {

    }

    override fun onTapBack() {
        onBackPressed()
    }
}
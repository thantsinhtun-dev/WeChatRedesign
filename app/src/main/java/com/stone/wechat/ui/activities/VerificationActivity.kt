package com.stone.wechat.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.stone.wechat.R
import com.stone.wechat.mvp.presenters.VerificationPresenter
import com.stone.wechat.mvp.presenters.VerificationPresenterImpl
import com.stone.wechat.mvp.views.VerificationView
import kotlinx.android.synthetic.main.activity_verification.*

class VerificationActivity : BaseActivity(), VerificationView{

    private var name:String = ""
    private var password:String =""
    private var dob:String = ""
    private var gender:String = ""

    private lateinit var mPresenter: VerificationPresenter
    override val layoutId: Int = R.layout.activity_verification

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
        fun getIntent(context: Context): Intent {
            return Intent(context, VerificationActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        name = intent.getStringExtra(EXTRA_NAME).toString()
        password = intent.getStringExtra(EXTRA_PASSWORD).toString()
        dob = intent.getStringExtra(EXTRA_DOB).toString()
        gender = intent.getStringExtra(EXTRA_GENDER).toString()

        setUpPresenter()
        setUpEditText()
        setUpListener()

        mPresenter.onUIReady(this)
        editPhone.setText("09779094484")



    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[VerificationPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun setUpListener() {
        btnGetOtp.setOnClickListener {
            showLoadingView()
            mPresenter.onTapGetOtp(this)

        }

        btnVerify.setOnClickListener {
            showLoadingView()
            mPresenter.onTapVerify(otp.text.toString())


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

    override fun navigateToSignUpActivity(phone: String, userId: String) {
        hideLoadingView()
        startActivity(SignUpActivity.getIntent(this,phone,userId))

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
        hideLoadingView()
        if(activate){
            btnVerify.isEnabled = true
            btnVerify.alpha = 1f
        }else{
            btnVerify.isEnabled = false
            btnVerify.alpha = 0.5f
        }
    }

    override fun showErrorMessage(title: String, body: String) {
        hideLoadingView()
        showFailureDialog(title,body)
    }

    override fun showMessage(message: String) {
        hideLoadingView()
        Snackbar.make(btnVerify.rootView,message,Snackbar.LENGTH_SHORT).show()
    }

    override fun showError(error: String) {
    }

    override fun onTapBack() {
        onBackPressed()
    }
}
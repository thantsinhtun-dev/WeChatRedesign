package com.stone.wechat.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.mvp.presenters.LoginPresenter
import com.stone.wechat.mvp.presenters.LoginPresenterImpl
import com.stone.wechat.mvp.views.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.editPhone
import kotlinx.android.synthetic.main.activity_login.imgBack
import kotlinx.android.synthetic.main.activity_verification.*

class LoginActivity : BaseActivity() ,LoginView{
    private lateinit var mPresenter:LoginPresenter
    companion object{
        fun getIntent(context: Context):Intent{
            return Intent(context,LoginActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpPresenter()
        setUpEditText()
        setUpListener()

        mPresenter.onUIReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[LoginPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun setUpListener() {
        btnLogin.setOnClickListener {
            showLoadingView()
            mPresenter.onTapLogin()
        }
        imgBack.setOnClickListener { mPresenter.onTapBackButton() }
    }

    private fun setUpEditText() {
        editPhone.addTextChangedListener {
            mPresenter.onChangePhoneNumber(it.toString())
        }
        editPassword.addTextChangedListener{
            mPresenter.onChangePassword(it.toString())
        }
    }

    override fun navigateToMainActivity() {
        hideLoadingView()
        startActivity(MainActivity.getIntent(this))
        finish()

    }

    override fun navigateToVerification() {
    }

    override fun activateLoginButton(activate: Boolean) {
        if(activate){
            btnLogin.isEnabled = true
            btnLogin.alpha = 1f
        }else{
            btnLogin.isEnabled = false
            btnLogin.alpha = 0.5f
        }
    }

    override fun showErrorMessage(title: String, body: String) {
        hideLoadingView()
        showFailureDialog(title, body)
    }

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun onTapBack() {
        onBackPressed()
    }
}
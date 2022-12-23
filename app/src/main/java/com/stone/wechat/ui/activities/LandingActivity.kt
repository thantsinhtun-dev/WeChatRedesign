package com.stone.wechat.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.mvp.presenters.LandingPresenter
import com.stone.wechat.mvp.presenters.LandingPresenterImpl
import com.stone.wechat.mvp.views.LandingView
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity(),LandingView {
    private lateinit var mPresenter: LandingPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        setUpPresenter()
        setUpListener()
    }

    private fun setUpListener() {
        btnLogin.setOnClickListener { navigateToLogin() }
        btnSignup.setOnClickListener { navigateToSignUp() }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[LandingPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    override fun navigateToSignUp() {
        startActivity(SignUpActivity.getIntent(this))
    }

    override fun navigateToLogin() {
        startActivity(LoginActivity.getIntent(this))
    }

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun onTapBack() {
        TODO("Not yet implemented")
    }
}
package com.stone.wechat.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.mvp.presenters.LandingPresenter
import com.stone.wechat.mvp.presenters.LandingPresenterImpl
import com.stone.wechat.mvp.views.LandingView
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity(),LandingView {
    private lateinit var mPresenter: LandingPresenter
    private var keepSplashScreen = true
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition{keepSplashScreen}

        setContentView(R.layout.activity_landing)

        setUpPresenter()
        setUpListener()
        mPresenter.onUIReady(this)
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
        startActivity(VerificationActivity.getIntent(this))
    }

    override fun navigateToLogin() {
        startActivity(LoginActivity.getIntent(this))
    }

    override fun userAlreadyExists() {
        startActivity(MainActivity.getIntent(this))
    }

    override fun userDoesNotExists() {
        keepSplashScreen = false
    }

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun onTapBack() {
        TODO("Not yet implemented")
    }
}
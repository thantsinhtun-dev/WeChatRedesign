package com.stone.wechat.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.mvp.presenters.SignUpPresenter
import com.stone.wechat.mvp.presenters.SignUpPresenterImpl
import com.stone.wechat.mvp.views.SignUpView
import com.stone.wechat.viewPods.CustomDropDown
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.imgBack


class SignUpActivity : BaseActivity(), SignUpView {

    private lateinit var mPresenter: SignUpPresenter

    private var phone:String = ""
    private var userId:String = ""
    companion object{
        private const val EXTRA_PHONE = "EXTRA_PHONE"
        private const val EXTRA_USERID = "EXTRA_USERID"
        fun getIntent(context: Context,phone: String,userId:String):Intent{
            val intent = Intent(context,SignUpActivity::class.java)
            intent.putExtra(EXTRA_PHONE,phone)
            intent.putExtra(EXTRA_USERID,userId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        phone = intent.getStringExtra(EXTRA_PHONE).toString()
        userId = intent.getStringExtra(EXTRA_USERID).toString()

        setUpPresenter()
        setUpSpinner()
        setUpRadioButton()
        setUpCheckBox()
        setUpEditText()
        setUpListener()
    }

    private fun setUpListener() {
        btnSignup.setOnClickListener {
            showLoadingView()
            mPresenter.onTapSignUp(phone,userId)
        }
        imgBack.setOnClickListener { mPresenter.onTapBackButton() }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[SignUpPresenterImpl::class.java]
        mPresenter.initView(this)
    }


    private fun setUpSpinner() {

        val days = IntArray(32) { it + 0 }
        val daysArray = days.map {
            if (it == 0) "Day" else it.toString()
        }.toTypedArray()

        val daysAdapter = CustomDropDown(
            context = applicationContext,
            R.layout.view_holder_drop_down,
            daysArray
        )
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spDay.adapter = daysAdapter
        spDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                mPresenter.onSelectDay(p0?.getItemAtPosition(p2).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        val months = IntArray(13) { it + 0 }
        val monthArray = months.map {
            if (it == 0) "Months" else it.toString()
        }.toTypedArray()

        val monthsAdapter = CustomDropDown(
            context = applicationContext,
            R.layout.view_holder_drop_down,
            monthArray
        )
        monthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spMonth.adapter = monthsAdapter
        spMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                mPresenter.onSelectMonth(p0?.getItemAtPosition(p2).toString())
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        val years = IntArray(20) { it + 1989 }
        val yearsArray = years.map {
            if (it == 1989) "Years" else it.toString()
        }.toTypedArray()

        val yearsAdapters = CustomDropDown(
            context = applicationContext,
            R.layout.view_holder_drop_down,
            yearsArray
        )
        yearsAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spYear.adapter = yearsAdapters
        spYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                mPresenter.onSelectYear(p0?.getItemAtPosition(p2).toString())
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


    }

    private fun setUpRadioButton() {
        rdGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.rdMale -> {
                    mPresenter.onChangeGender("Male")
                }
                R.id.rdFemale -> {
                    mPresenter.onChangeGender("Female")
                }
                R.id.rdOther -> {
                    mPresenter.onChangeGender("Other")
                }
            }

        }
    }

    private fun setUpCheckBox() {
        cbAgreeTo.setOnCheckedChangeListener { _, isChecked ->
            mPresenter.onChangeCheckBox(isChecked)
        }
    }

    private fun setUpEditText() {
        editName.addTextChangedListener {
            mPresenter.onChangeName(it.toString())
        }
        editPassword.addTextChangedListener {
            mPresenter.onChangePassword(it.toString())
        }

    }

    override fun navigateToMainActivity() {
        hideLoadingView()
        startActivity(MainActivity.getIntent(this))
    }

    override fun navigateToLandingScreen() {
        onBackPressed()
    }

    override fun activateSignUpButton(activate: Boolean) {
        if (activate){
            btnSignup.isEnabled = true
            btnSignup.alpha = 1f
        }else{
            btnSignup.alpha = 0.5f
            btnSignup.isEnabled = false
        }
    }

    override fun showErrorDialog() {
    }

    override fun showError(error: String) {
        hideLoadingView()
        showFailureDialog("ERROR !",error)
    }

    override fun onTapBack() {
        onBackPressed()
    }
}
package com.stone.wechat.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.mvp.presenters.SignUpPresenter
import com.stone.wechat.mvp.presenters.SignUpPresenterImpl
import com.stone.wechat.mvp.views.SignUpView
import com.stone.wechat.viewPods.CustomDropDown
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.imgBack
import kotlinx.android.synthetic.main.activity_verification.*


class SignUpActivity : AppCompatActivity(), SignUpView {

    private lateinit var mPresenter: SignUpPresenter

    companion object{
        fun getIntent(context: Context):Intent{
            return Intent(context,SignUpActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setUpPresenter()
        setUpSpinner()
        setUpRadioButton()
        setUpCheckBox()
        setUpEditText()
        setUpListener()
    }

    private fun setUpListener() {
        btnSignup.setOnClickListener {
            mPresenter.onTapSignUp()
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
                    Toast.makeText(this, "male", Toast.LENGTH_SHORT).show()
                }
                R.id.rdFemale -> {
                    mPresenter.onChangeGender("Female")
                    Toast.makeText(this, "female", Toast.LENGTH_SHORT).show()
                }
                R.id.rdOther -> {
                    mPresenter.onChangeGender("Other")
                    Toast.makeText(this, "other", Toast.LENGTH_SHORT).show()
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
        editName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mPresenter.onChangeName(p0.toString())
                Toast.makeText(applicationContext,p0.toString() , Toast.LENGTH_SHORT).show()

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        editPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mPresenter.onChangePassword(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

    }

    override fun navigateToVerification(
        name: String,
        dob: String,
        gender: String,
        password: String
    ) {
        TODO("Not yet implemented")
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
    }

    override fun onTapBack() {
        onBackPressed()
    }
}
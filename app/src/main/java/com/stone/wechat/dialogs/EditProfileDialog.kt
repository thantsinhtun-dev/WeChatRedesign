package com.stone.wechat.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.stone.wechat.R
import com.stone.wechat.data.vos.UserVO
import com.stone.wechat.mvp.presenters.EditProfilePresenter
import com.stone.wechat.mvp.presenters.EditProfilePresenterImpl
import com.stone.wechat.mvp.views.EditProfileView
import com.stone.wechat.viewPods.CustomDropDown
import kotlinx.android.synthetic.main.dialog_edit_profile.*

class EditProfileDialog(private val userVO: UserVO) : DialogFragment(),EditProfileView {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_edit_profile, container, false)
    }

    private lateinit var mPresenter:EditProfilePresenter
    private var selectedDay: String = ""
    private var selectedMonth: String = ""
    private var selectedYear: String = ""
    private var selectedGender: String = "Male"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpSpinner()
        setUpListener()
        setUpUI()
        setUpRadioButton()
    }

    private fun setUpListener() {
        btnCancel.setOnClickListener { mPresenter.onTapDismiss() }
        btnSave.setOnClickListener {

            mPresenter.onTapSave(
                userName = editName.text.toString(),
                phone = editPhone.text.toString(),
                dob = selectedDay.plus("/").plus(selectedMonth).plus("/").plus(selectedYear),
                gender = selectedGender,
                password = userVO.password ?: "",
                profile = userVO.profile ?: "",
                userId = userVO.userId ?: "",
                qr = userVO.qrCode ?: ""
            )
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[EditProfilePresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun setUpUI() {
        editName.setText(userVO.name)
        editPhone.setText(userVO.phone)
        selectedGender = userVO.gender.toString()
        when (userVO.gender) {
            "Male" -> {
                rdMale.isChecked = true
            }
            "Female" -> {
                rdFemale.isChecked = true
            }
            else -> rdOther.isChecked = true
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    private fun setUpSpinner() {
        val dob = userVO.dob?.split("/")
        val day = dob?.get(0)
        val month = dob?.get(1)
        val year = dob?.get(2)
        val days = IntArray(32) { it + 0 }
        val daysArray = days.map {
            if (it == 0) "Day" else it.toString()
        }.toTypedArray()

        val daysAdapter = CustomDropDown(
            context = requireContext(),
            R.layout.view_holder_drop_down,
            daysArray
        )
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spDay.adapter = daysAdapter
        spDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               selectedDay = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        spDay.setSelection(daysAdapter.getPosition(day))

        val months = IntArray(13) { it + 0 }
        val monthArray = months.map {
            if (it == 0) "Months" else it.toString()
        }.toTypedArray()

        val monthsAdapter = CustomDropDown(
            context = requireContext(),
            R.layout.view_holder_drop_down,
            monthArray
        )
        monthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spMonth.adapter = monthsAdapter
        spMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               selectedMonth = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        spMonth.setSelection(monthsAdapter.getPosition(month))

        val years = IntArray(20) { it + 1989 }
        val yearsArray = years.map {
            if (it == 1989) "Years" else it.toString()
        }.toTypedArray()

        val yearsAdapters = CustomDropDown(
            context = requireContext(),
            R.layout.view_holder_drop_down,
            yearsArray
        )
        yearsAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spYear.adapter = yearsAdapters
        spYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               selectedYear = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        spYear.setSelection(yearsAdapters.getPosition(year))


    }
    private fun setUpRadioButton() {
        rdGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.rdMale -> {
                    selectedGender ="Male"
                }
                R.id.rdFemale -> {
                    selectedGender = "Female"
                }
                R.id.rdOther -> {
                    selectedGender = "Other"
                }
            }

        }
    }

    override fun cancelDialog() {
        dialog?.dismiss()
    }

    override fun navigateBack() {
        dialog?.dismiss()
        Toast.makeText(requireContext(), "Update Profile Successfully", Toast.LENGTH_SHORT).show()

    }

    override fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    override fun onTapBack() {
        TODO("Not yet implemented")
    }
}
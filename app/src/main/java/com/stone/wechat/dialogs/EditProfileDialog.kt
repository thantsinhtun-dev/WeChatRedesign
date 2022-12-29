package com.stone.wechat.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.stone.wechat.R
import com.stone.wechat.viewPods.CustomDropDown
import kotlinx.android.synthetic.main.dialog_edit_profile.*

class EditProfileDialog: DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            return inflater.inflate(R.layout.dialog_edit_profile, container, false)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpinner()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    private fun setUpSpinner() {

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
//                mPresenter.onSelectDay(p0?.getItemAtPosition(p2).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

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
//                mPresenter.onSelectMonth(p0?.getItemAtPosition(p2).toString())
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

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
//                mPresenter.onSelectYear(p0?.getItemAtPosition(p2).toString())
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


    }
}
package com.stone.wechat.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.stone.wechat.R
import kotlinx.android.synthetic.main.dialog_failure.view.*

abstract class BaseFragment: Fragment() {
    private var progressDialog: Dialog? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addProgressBar()
    }
    private fun addProgressBar() {

        val dialog = Dialog(requireContext(), R.style.ThemeLoadingDialog)
        dialog.setContentView(com.stone.wechat.R.layout.loading)
        dialog.setCancelable(false)
        progressDialog = dialog
    }
    fun showLoadingView() {
        progressDialog?.show()
    }

    fun hideLoadingView() {
        progressDialog?.hide()
    }
    fun showAlertDialog(){

    }
    fun showFailureDialog(title:String,body:String){
        val view: View = View.inflate(requireContext(), R.layout.dialog_failure, null)
        view.lblTitle.text = title
        view.lblBody.text = body
        val adb = androidx.appcompat.app.AlertDialog.Builder(requireContext(), R.style.ThemeDialog).setView(view).show()
        adb.setView(view)

        view.btnOK.setOnClickListener { adb.dismiss() }

    }

}
package com.stone.wechat.ui.activities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.stone.wechat.R
import kotlinx.android.synthetic.main.dialog_failure.view.*


abstract class BaseActivity: AppCompatActivity() {

    private var progressDialog: Dialog? = null
    abstract val layoutId:Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        addProgressBar()
    }
    private fun addProgressBar() {

        val dialog = Dialog(this, com.stone.wechat.R.style.ThemeLoadingDialog)
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
        val view: View = View.inflate(this, R.layout.dialog_failure, null)
        view.lblTitle.text = title
        view.lblBody.text = body
        val adb = androidx.appcompat.app.AlertDialog.Builder(this,R.style.ThemeDialog).setView(view).show()
        adb.setView(view)

        view.btnOK.setOnClickListener { adb.dismiss() }

    }
}
package com.stone.wechat.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stone.wechat.R
import com.stone.wechat.dialogs.EditProfileDialog
import kotlinx.android.synthetic.main.dialog_edit_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpListener()
    }

    private fun setUpListener() {
        btnProfileEdit.setOnClickListener {
            EditProfileDialog().show(childFragmentManager.beginTransaction(),"")
//            showProfileEditDialog("mg mg","0999999")
        }
    }
    private fun showProfileEditDialog(name:String, phone:String){
        val view: View = View.inflate(requireContext(), R.layout.dialog_edit_profile, null)
//        view.editName.setText(name)
//        view.editPhone.setText(phone)
        val adb = androidx.appcompat.app.AlertDialog.Builder(requireContext()).setView(view).show()
        adb.setView(view)

        view.btnCancel.setOnClickListener { adb.dismiss() }


    }


}
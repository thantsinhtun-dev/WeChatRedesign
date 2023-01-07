package com.stone.wechat.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.stone.wechat.R
import com.stone.wechat.data.models.ContactModelImpl
import com.stone.wechat.ui.activities.LandingActivity
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogout.setOnClickListener {
            ContactModelImpl.changeOnlineStatus(isOnline = false, onSuccess = {}, onFailure = {})
            FirebaseAuth.getInstance().signOut()
            startActivity(LandingActivity.getIntent(requireContext()))
        }
    }


}
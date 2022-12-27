package com.stone.wechat.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.stone.wechat.R
import com.stone.wechat.ui.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        fun getIntent(context: Context):Intent{
            val intent = Intent(context,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return  intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        bottomNavigation.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.menu_moment->
                    setCurrentFragment(MomentFragment())
                R.id.menu_chat->
                    setCurrentFragment(ChatFragment())
                R.id.menu_contacts->
                    setCurrentFragment(ContactFragment())
                R.id.menu_me->
                    setCurrentFragment(ProfileFragment())
                R.id.menu_settings->
                    setCurrentFragment(SettingsFragment())
            }
            true
        }
        bottomNavigation.selectedItemId = R.id.menu_moment
    }

    private fun setCurrentFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }
}
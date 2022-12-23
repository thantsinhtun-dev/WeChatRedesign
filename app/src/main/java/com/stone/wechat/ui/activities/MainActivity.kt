package com.stone.wechat.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stone.wechat.R

class MainActivity : AppCompatActivity() {
    companion object{
        fun getIntent(context: Context):Intent{
            return Intent(context,MainActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
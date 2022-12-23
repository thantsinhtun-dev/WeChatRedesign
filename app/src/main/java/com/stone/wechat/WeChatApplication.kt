package com.stone.wechat

import android.app.Application
import android.content.res.Resources

class WeChatApplication: Application() {
    var res: Resources? = null

    override fun onCreate() {
        super.onCreate()
        res = Resources.getSystem()
    }
}
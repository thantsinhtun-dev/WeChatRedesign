package com.stone.wechat

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.stone.wechat.data.models.ContactModel
import com.stone.wechat.data.models.ContactModelImpl

class WeChatApplication: Application(), Application.ActivityLifecycleCallbacks {
    private var activityReferences = 0
    private var isActivityChangingConfigurations = false
    private lateinit var mContactModel:ContactModel

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        mContactModel = ContactModelImpl
        registerActivityLifecycleCallbacks(this);

    }
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
//        Log.i("MyERRRR","foreground onActivityCreated")
    }

    override fun onActivityStarted(activity: Activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            // App enters foreground
            Log.i("MyERRRR","foreground onActivityStarted")
            mContactModel.changeOnlineStatus(isOnline = true, onSuccess={
                Log.i("MyERRRR","foreground onActivityStarted")

            }, onFailure={})
        }
    }

    override fun onActivityResumed(activity: Activity) {
//        Log.i("MyERRRR","foregroun onActivityResumed")
    }

    override fun onActivityPaused(activity: Activity) {
//        Log.i("MyERRRR","background onActivityPaused")
    }

    override fun onActivityStopped(activity: Activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations;
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
                    Log.i("MyERRRR","background onActivityPaused")

            mContactModel.changeOnlineStatus(isOnline = false, onSuccess={}, onFailure={})
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
//        Log.i("MyERRRR","background onActivitySaveInstanceState")
    }

    override fun onActivityDestroyed(activity: Activity) {
//        Log.i("MyERRRR","background onActivityDestroyed")
    }


}
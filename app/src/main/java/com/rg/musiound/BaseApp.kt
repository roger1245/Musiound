package com.rg.musiound

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.billy.android.swipe.SmartSwipeBack
import com.rg.musiound.view.activity.MainActivity
import com.rg.musiound.view.activity.SplashActivity

/**
 * Create by roger
 * on 2019/8/15
 */
class BaseApp : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        SmartSwipeBack.activityBezierBack(this) {
            (it !is MainActivity ) && (it !is SplashActivity)
        }
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        context = base
    }
}
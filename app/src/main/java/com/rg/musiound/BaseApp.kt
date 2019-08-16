package com.rg.musiound

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

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
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        context = base
    }
}
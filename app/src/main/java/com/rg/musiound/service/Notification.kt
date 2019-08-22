package com.rg.musiound.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Create by roger
 * on 2019/8/22
 */
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ACTION_PAUSE -> {
                PlayManager.instance.dispatch()
            }
            ACTION_NEXT -> {
                PlayManager.instance.next()
            }
            ACTION_DELETE -> {
                PlayManager.instance.stop()
                PlayManager.instance.cancelNotification()
            }
        }
    }

}
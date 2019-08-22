package com.rg.musiound.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.githang.statusbar.StatusBarCompat
import com.rg.musiound.BaseApp
import com.rg.musiound.R


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        StatusBarCompat.setStatusBarColor(this, Color.parseColor(BaseApp.context.getString(R.color.activity_splash_red_background as Int)))
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }
}

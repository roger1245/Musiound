package com.rg.musiound.view

import android.graphics.Color
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.rg.musiound.BaseApp
import com.rg.musiound.R
import kotlinx.android.synthetic.main.toolbar_common.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Create by roger
 * on 2019/8/17
 */
abstract class BaseActivity : AppCompatActivity() {

    val common_toolbar get() = toolbar

    protected fun Toolbar.init(
        title: String,
        @DrawableRes icon: Int = R.drawable.toolbar_common_ic_back,
        listener: View.OnClickListener? = View.OnClickListener { finish() }
    ) {
        this.title = title
        setSupportActionBar(this)
        if (listener == null) {
            navigationIcon = null
        } else {
            setNavigationIcon(icon)
            setNavigationOnClickListener(listener)
        }
    }

    protected fun Toolbar.initBlack(
        title: String,
        @DrawableRes icon: Int = R.drawable.toolbar_common_ic_back_white,
        listener: View.OnClickListener? = View.OnClickListener { finish() }
    ) {
        this.title = title
        setSupportActionBar(this)
        if (listener == null) {
            navigationIcon = null
        } else {
            setNavigationIcon(icon)
            setNavigationOnClickListener(listener)
        }
    }

//    override fun onStart() {
//        super.onStart()
//        EventBus.getDefault().register(this)
//    }
//
//    override fun onStop() {
//        super.onStop()
//        EventBus.getDefault().unregister(this)
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    open fun onEvent(event: LoginStateChangeEvent) {
//    }
}
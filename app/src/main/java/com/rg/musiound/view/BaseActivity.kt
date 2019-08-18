package com.rg.musiound.view

import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.rg.musiound.R
import kotlinx.android.synthetic.main.toolbar_common.*
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
}
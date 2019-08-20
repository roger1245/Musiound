package com.rg.musiound.util

import com.rg.musiound.BaseApp.Companion.context

/**
 * Create by roger
 * on 2019/8/20
 */
fun dp2px(value: Int): Int {
    val v = context.resources.displayMetrics.density
    return (v * value + 0.5f).toInt()
}
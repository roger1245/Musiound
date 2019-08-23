package com.rg.musiound.util.extensions

import android.content.Context
import android.content.SharedPreferences

/**
 * Create by roger
 * on 2019/8/23
 */
const val DEFAULT_PREFERENCE_FILENAME = "share_data"
val Context.defaultSharedPreferences get() = sharedPreferences(DEFAULT_PREFERENCE_FILENAME)

fun Context.sharedPreferences(name: String): SharedPreferences = getSharedPreferences(name, Context.MODE_PRIVATE)
fun SharedPreferences.editor(editorBuilder: SharedPreferences.Editor.() -> Unit) = edit().apply(editorBuilder).apply()
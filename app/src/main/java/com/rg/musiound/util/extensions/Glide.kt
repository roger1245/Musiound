package com.rg.musiound.util.extensions

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rg.musiound.BaseApp
import com.rg.musiound.R

/**
 * Create by roger
 * on 2019/8/16
 */
fun ImageView.setImageFromUrl(url: String?) = context.loadImage(url, this)

fun Context.loadImage(rowUrl: String?,
                             imageView: ImageView,
                             @DrawableRes placeholder: Int = R.drawable.glide_place_holder,
                             @DrawableRes error: Int = R.drawable.glide_place_holder) {
    val url = when {
        rowUrl.isNullOrEmpty() -> {
            imageView.setImageResource(error)
            return
        }
        rowUrl.startsWith("http://") || rowUrl.startsWith("https://") -> rowUrl
        else -> {}
    }
    Glide.with(this)
        .load(url)
        // Glide 加载动画bug
//            .transition(DrawableTransitionOptions().crossFade())
        .apply(RequestOptions().placeholder(placeholder).error(error))
        .into(imageView)
}


fun dp2px(dpValue :Int) = (dpValue * BaseApp.context.resources.displayMetrics.density + 0.5f).toInt()
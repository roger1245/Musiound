package com.rg.musiound.bean

import com.rg.musiound.bean.songlistdetail.Ar

/**
 * Create by roger
 * on 2019/8/16
 */
data class Song(
    val name: String, val url: String,
    val pic: String, val singer: List<Ar>
)
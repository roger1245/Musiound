package com.rg.musiound.bean

/**
 * Create by roger
 * on 2019/8/18
 */
data class MVDetailRaw(
    val code: Int,
    val msg: String,
    val data: MVDetailWrapper
)
data class MVDetailWrapper(
    val data: MVDetail
)
data class MVDetail (
    val cover: String,
    val name: String,
    val publishTime: String,
    val desc: String,
    val alias: List<String>,
    val artistName: String,
    val playCount: Long,
    val id:  Long
)
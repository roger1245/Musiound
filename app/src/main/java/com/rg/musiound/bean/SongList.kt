package com.rg.musiound.bean

/**
 * Create by roger
 * on 2019/8/16
 */
data class SongList(
    val description: String,
    val id: Long,
    val tag: String,
    val copywriter: String,
    val subscribers: List<Subscriber>
)
data class Subscriber(
    val backgroundUrl: String,
    val avatarUrl: String
)

data class SongListRaw(
    val code: Int,
    val msg: String,
    val data: List<SongList>
)
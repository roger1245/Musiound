package com.rg.musiound.bean

/**
 * Create by roger
 * on 2019/8/18
 */
data class MVListRaw(
    val code: Int,
    val msg: String,
    val data: List<MV>
)
data class MV(
    val cover: String,
    val id: Long,
    val name: String,
    val artistName: String,
    val lastRank: Int,
    val playCount: Long
)
data class MVCommentRaw(
    val code: Int,
    val data: MVCommentData
)
data class MVCommentData(
    val comments: List<MVComment>,
    val more: Boolean
)
data class MVComment(
    val content: String,
    val user: User,
    val beReplied: List<BeReplied>
)
data class BeReplied(
    val user: User,
    val content: String
)
data class User(
    val avatarUrl: String,
    val nickname: String,
    val userId: Long
)
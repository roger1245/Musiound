package com.rg.musiound.bean

/**
 * Create by roger
 * on 2019/8/17
 */
data class ListDetailRaw(
    val code: Int,
    val msg: String,
    val data: ListDetail
)
data class ListDetail(
    val description: String,
    val trackCount: Int,
    val id: Long,
    val tracks: List<Track>,
    val coverImgUrl: String,
    val name: String,
    val tags: List<String>
)
data class Track(
    val id: Long,
    val name: String,
    val artists: List<Artist>
)
data class Artist(
    val name: String
)

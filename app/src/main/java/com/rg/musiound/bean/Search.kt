package com.rg.musiound.bean

/**
 * Create by roger
 * on 2019/8/19
 */
//歌单
data class SongListSearchRaw(
    val data: SongListSearch
)
data class SongListSearch(
    val playlists: List<SongListSubSearch>
)
data class SongListSubSearch(
    val coverImgUrl: String,
//    val playCount: Int,
    val name: String,
    val description: String?,
    val id: Long
)
//单曲
data class SongSearchRaw(
    val code: Int,
    val data: SongSearch
)
data class SongSearch(
    val songs: List<SongSub>,
    val songCount: Int
)
data class SongSub(
    val id: Long,
    val name: String,
    val ar: List<ArtistSub>
)
data class ArtistSub(
    val name: String
)
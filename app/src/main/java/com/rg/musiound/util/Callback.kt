package com.rg.musiound.util

import com.rg.musiound.bean.*

/**
 * Create by roger
 * on 2019/8/16
 */
interface SongCallback {
    fun onSuccess(song: Song)
    fun onFailed()
}
interface SongListCallback {
    fun onSuccess(songList: List<SongList>)
    fun onFailed()
}
interface SongsCallback {
    fun onSuccess(songs: ListDetailRaw)
    fun onFailed()
}
interface MVListCallback {
    fun onSuccess(songs: List<MV>)
    fun onFailed()
}
interface  GenericCallback <T> {
    fun onSuccess(data: T)
    fun onFailed()
}
interface  GenericPageCallback <T> {
    fun onSuccess(data: T, page: Int = 0)
    fun onFailed()
}


interface OnItemClickListener {
    fun onItemClick(position: Int)
}



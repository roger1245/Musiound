package com.rg.musiound.util

import com.rg.musiound.bean.ListDetailRaw
import com.rg.musiound.bean.Song
import com.rg.musiound.bean.SongList

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


interface OnItemClickListener {
    fun onItemClick(position: Int)
}

package com.rg.musiound.service.ruler

import com.rg.musiound.bean.Song


interface Rule {
    val rule: Int
    fun previous(song: Song, songList: List<Song>, isUserAction: Boolean): Song

    fun next(song: Song, songList: List<Song>, isUserAction: Boolean): Song

    fun clear()
}

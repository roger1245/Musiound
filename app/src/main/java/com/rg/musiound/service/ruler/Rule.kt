package com.rg.musiound.service.ruler

import com.rg.musiound.service.Song


interface Rule {
    fun previous(song: Song, songList: List<Song>, isUserAction: Boolean): Song

    fun next(song: Song, songList: List<Song>, isUserAction: Boolean): Song

    fun clear()
}

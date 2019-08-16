package com.rg.musiound.util

import com.rg.musiound.bean.Song

/**
 * Create by roger
 * on 2019/8/16
 */
interface SongCallback {
    fun onSuccess(song: Song)
    fun onFailed()
}
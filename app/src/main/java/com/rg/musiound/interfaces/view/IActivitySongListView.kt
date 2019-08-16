package com.rg.musiound.interfaces.view

import com.rg.musiound.base.IBaseView
import com.rg.musiound.bean.SongList

/**
 * Create by roger
 * on 2019/8/16
 */
interface IActivitySongListView : IBaseView {
    fun setSongList(songList: List<SongList>)
    fun showError()
}
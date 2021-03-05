package com.rg.musiound.interfaces.view

import com.rg.musiound.base.IBaseView
import com.rg.musiound.bean.ListDetailRaw
import com.rg.musiound.bean.Song
import com.rg.musiound.bean.songlistdetail.SongListDetailRoot

/**
 * Create by roger
 * on 2019/8/17
 */
interface IActivityListDetailView: IBaseView {
    fun setSongs(songs: SongListDetailRoot)
    fun showError()
}
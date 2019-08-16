package com.rg.musiound.interfaces.presenter

import com.rg.musiound.base.IBasePresenter
import com.rg.musiound.bean.SongList
import com.rg.musiound.interfaces.model.IActivitySongListModel
import com.rg.musiound.interfaces.view.IActivitySongListView

/**
 * Create by roger
 * on 2019/8/16
 */
interface IActivitySongListPresenter : IBasePresenter<IActivitySongListView, IActivitySongListModel> {
    fun onSongListLoad(songList: List<SongList>)
    fun onDataNotAvailable()
    fun populateData()
    fun start()
}
package com.rg.musiound.interfaces.presenter

import com.rg.musiound.base.IBasePresenter
import com.rg.musiound.bean.ListDetailRaw
import com.rg.musiound.bean.Song
import com.rg.musiound.bean.SongList
import com.rg.musiound.interfaces.model.IActivityListDetailModel
import com.rg.musiound.interfaces.view.IActivityListDetailView

/**
 * Create by roger
 * on 2019/8/17
 */
interface IActivityListDetailPresenter : IBasePresenter<IActivityListDetailView, IActivityListDetailModel> {
    fun onSongsLoad(song: ListDetailRaw)
    fun onDataNotAvailable()
    fun populateData(songList: Long)
    fun start(songList: Long)
}
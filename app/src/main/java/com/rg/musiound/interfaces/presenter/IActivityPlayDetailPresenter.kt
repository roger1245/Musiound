package com.rg.musiound.interfaces.presenter

import com.rg.musiound.base.IBasePresenter
import com.rg.musiound.bean.Song
import com.rg.musiound.interfaces.model.IActivityPlayDetailModel
import com.rg.musiound.interfaces.view.IActivityPlayDetailView

/**
 * Create by roger
 * on 2019/8/16
 */
interface IActivityPlayDetailPresenter : IBasePresenter<IActivityPlayDetailView, IActivityPlayDetailModel> {
    fun onSongLoad(song: Song)
    fun onDataNotAvailable()
    fun populateData()
    fun start()
}
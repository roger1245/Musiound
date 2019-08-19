package com.rg.musiound.interfaces.presenter

import com.rg.musiound.base.IBasePresenter
import com.rg.musiound.bean.MV
import com.rg.musiound.interfaces.model.IFragmentMusicRankModel
import com.rg.musiound.interfaces.view.IFragmentMusicRankView

/**
 * Create by roger
 * on 2019/8/18
 */
interface IFragmentMusicRankPresenter : IBasePresenter<IFragmentMusicRankView, IFragmentMusicRankModel> {
    fun onMVListLoad(mvList: List<MV>)
    fun onDataNotAvailable()
    fun populateData()
    fun start()
}
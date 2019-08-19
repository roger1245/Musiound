package com.rg.musiound.interfaces.presenter

import com.rg.musiound.base.IBasePresenter
import com.rg.musiound.interfaces.model.IActivityMVPlayerModel
import com.rg.musiound.interfaces.view.IActivityMVPlayerView

/**
 * Create by roger
 * on 2019/8/18
 */
interface IActivityMVPlayerPresenter <T1, T2> : IBasePresenter<IActivityMVPlayerView<T1, T2>, IActivityMVPlayerModel<T1, T2>> {
    fun onMVDetail(t: T1)
    fun onDataNotAvailable()
    fun onLoadMoreData(t: T2, page: Int = 0)
    fun populateData(id: Long)
    fun start(id: Long)
    fun loadMoreData(id: Long, page: Int)
}
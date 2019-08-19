package com.rg.musiound.interfaces.generic

import com.rg.musiound.base.IBasePresenter
import com.rg.musiound.interfaces.model.IActivityMVPlayerModel
import com.rg.musiound.interfaces.view.IActivityMVPlayerView

/**
 * Create by roger
 * on 2019/8/18
 */
interface IActivityGenericPresenter <T, P> : IBasePresenter<IActivityGenericView<T, P>, IActivityGenericModel<T, P>> {
    fun onDataLoad(data: T, page: Int)
    fun onDataNotAvailable()
    fun loadMoreData(uri: P, page: Int)
}
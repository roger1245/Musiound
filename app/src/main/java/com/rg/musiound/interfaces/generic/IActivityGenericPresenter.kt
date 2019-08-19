package com.rg.musiound.interfaces.generic

import com.rg.musiound.base.IBasePresenter
import com.rg.musiound.interfaces.model.IActivityMVPlayerModel
import com.rg.musiound.interfaces.view.IActivityMVPlayerView

/**
 * Create by roger
 * on 2019/8/18
 */
interface IActivityGenericPresenter <T> : IBasePresenter<IActivityGenericView<T>, IActivityGenericModel<T>> {
    fun onDataLoad(data: T)
    fun onDataNotAvailable()
    fun populateData(id: Long)
    fun start(id: Long)
}
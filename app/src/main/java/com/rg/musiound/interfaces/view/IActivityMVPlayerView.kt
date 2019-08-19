package com.rg.musiound.interfaces.view

import com.rg.musiound.base.IBaseView

/**
 * Create by roger
 * on 2019/8/18
 */
interface IActivityMVPlayerView <T1, T2>: IBaseView {
    fun setMVDetail(t: T1)
    fun showError()
    fun onLoadMoreData(t: T2, page: Int)
}
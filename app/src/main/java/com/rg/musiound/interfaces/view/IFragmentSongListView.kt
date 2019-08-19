package com.rg.musiound.interfaces.view

import com.rg.musiound.base.IBaseView

/**
 * Create by roger
 * on 2019/8/19
 */
interface IFragmentSongListView<T> : IBaseView {
    fun showError()
    fun onLoadMoreData(t: T, page: Int)
}
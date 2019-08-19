package com.rg.musiound.interfaces.presenter

import com.rg.musiound.base.IBasePresenter
import com.rg.musiound.bean.SongList
import com.rg.musiound.interfaces.model.IFragmentSongListModel
import com.rg.musiound.interfaces.view.IFragmentSongListView

/**
 * Create by roger
 * on 2019/8/19
 */
interface IFragmentSongListPresenter<T> : IBasePresenter<IFragmentSongListView<T>, IFragmentSongListModel<T>> {
    fun onSongListLoad(songList: T, page: Int)
    fun onDataNotAvailable()
    fun loadMoreData(page: Int = 0, category: String)
}
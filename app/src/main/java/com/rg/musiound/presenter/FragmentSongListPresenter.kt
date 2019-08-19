package com.rg.musiound.presenter

import com.rg.musiound.base.BasePresenter
import com.rg.musiound.interfaces.model.IFragmentSongListModel
import com.rg.musiound.interfaces.presenter.IFragmentSongListPresenter
import com.rg.musiound.interfaces.view.IFragmentSongListView
import com.rg.musiound.model.FragmentSongListModel
import com.rg.musiound.util.GenericPageCallback

/**
 * Create by roger
 * on 2019/8/19
 */
class FragmentSongListPresenter <T>: BasePresenter<IFragmentSongListView<T>, IFragmentSongListModel<T>>(), IFragmentSongListPresenter<T> {
    override fun attachModel(): IFragmentSongListModel<T> {
        return FragmentSongListModel<T>()
    }

    override fun onSongListLoad(songList: T, page: Int) {
        view?.onLoadMoreData(songList, page)
    }

    override fun onDataNotAvailable() {

    }

    override fun loadMoreData(page: Int, category: String) {

        model?.getData(object : GenericPageCallback<T> {
            override fun onFailed() {
                onDataNotAvailable()
            }

            override fun onSuccess(data: T, page: Int) {
                onSongListLoad(data, page)
            }

        }, page, category)
    }

}
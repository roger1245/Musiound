package com.rg.musiound.presenter

import com.rg.musiound.base.BasePresenter
import com.rg.musiound.bean.MV
import com.rg.musiound.interfaces.generic.IActivityGenericModel
import com.rg.musiound.interfaces.generic.IActivityGenericPresenter
import com.rg.musiound.interfaces.generic.IActivityGenericView
import com.rg.musiound.interfaces.model.IFragmentMusicRankModel
import com.rg.musiound.interfaces.presenter.IFragmentMusicRankPresenter
import com.rg.musiound.interfaces.view.IFragmentMusicRankView
import com.rg.musiound.model.FragmentMusicRankModel
import com.rg.musiound.model.FragmentSongListSearchModel
import com.rg.musiound.util.GenericCallback
import com.rg.musiound.util.MVListCallback

/**
 * Create by roger
 * on 2019/8/18
 */
class FragmentMusicRankPresenter<T, P> : BasePresenter<IActivityGenericView<T, P>, IActivityGenericModel<T, P>>(),
    IActivityGenericPresenter<T, P> {
    override fun onDataLoad(data: T, page: Int) {
        view?.setData(data, page)
    }

    override fun onDataNotAvailable() {
        view?.showError()
    }

    override fun loadMoreData(uri: P, page: Int) {
        model?.getData(uri, page, object : GenericCallback<T> {
            override fun onFailed() {
                onDataNotAvailable()
            }

            override fun onSuccess(data: T) {
                onDataLoad(data, page)
            }
        })

    }

    override fun attachModel(): IActivityGenericModel<T, P> {
        return FragmentMusicRankModel<T, P>()
    }

}
package com.rg.musiound.presenter

import com.rg.musiound.base.BasePresenter
import com.rg.musiound.bean.MV
import com.rg.musiound.interfaces.model.IFragmentMusicRankModel
import com.rg.musiound.interfaces.presenter.IFragmentMusicRankPresenter
import com.rg.musiound.interfaces.view.IFragmentMusicRankView
import com.rg.musiound.model.FragmentMusicRankModel
import com.rg.musiound.util.MVListCallback

/**
 * Create by roger
 * on 2019/8/18
 */
class FragmentMusicRankPresenter: BasePresenter<IFragmentMusicRankView, IFragmentMusicRankModel>(), IFragmentMusicRankPresenter {
    override fun attachModel(): IFragmentMusicRankModel {
        return FragmentMusicRankModel()
    }

    override fun onMVListLoad(mvList: List<MV>) {
        view?.setVideoList(mvList)
    }

    override fun onDataNotAvailable() {
        view?.showError()
    }

    override fun populateData() {
        model?.getData(object : MVListCallback {
            override fun onSuccess(songs: List<MV>) {
                onMVListLoad(songs)
            }

            override fun onFailed() {
                onDataNotAvailable()
            }

        })

    }

    override fun start() {
        populateData()
    }

}
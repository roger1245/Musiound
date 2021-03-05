package com.rg.musiound.view.songlistdetail

import com.rg.musiound.base.BasePresenter
import com.rg.musiound.bean.songlistdetail.SongListDetailRoot
import com.rg.musiound.bean.songlisttag.SongListRoot
import com.rg.musiound.interfaces.model.IActivityListDetailModel
import com.rg.musiound.interfaces.presenter.IActivityListDetailPresenter
import com.rg.musiound.interfaces.view.IActivityListDetailView
import com.rg.musiound.util.SongsCallback

/**
 * Create by roger
 * on 2019/8/17
 */
class ActivityListDetailPresenter: BasePresenter<IActivityListDetailView, IActivityListDetailModel>(), IActivityListDetailPresenter {
    override fun attachModel(): IActivityListDetailModel {
        return ActivityListDetailModel()
    }

    override fun onSongsLoad(song: SongListDetailRoot) {
        view?.setSongs(song)
    }

    override fun onDataNotAvailable() {
        view?.showError()
    }

    override fun populateData(songList: Long) {
        model?.getData(object: SongsCallback {
            override fun onSuccess(songs: SongListDetailRoot) {
                onSongsLoad(songs)
            }

            override fun onFailed() {
                onDataNotAvailable()
            }

        }, songList)
    }

    override fun start(songList: Long) {
        populateData(songList)
    }
}
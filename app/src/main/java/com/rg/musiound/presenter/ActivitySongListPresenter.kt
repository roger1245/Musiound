package com.rg.musiound.presenter

import android.util.Log
import com.rg.musiound.base.BasePresenter
import com.rg.musiound.base.IBasePresenter
import com.rg.musiound.bean.Song
import com.rg.musiound.bean.SongList
import com.rg.musiound.interfaces.model.IActivityPlayDetailModel
import com.rg.musiound.interfaces.model.IActivitySongListModel
import com.rg.musiound.interfaces.presenter.IActivitySongListPresenter
import com.rg.musiound.interfaces.view.IActivitySongListView
import com.rg.musiound.model.ActivityPlayDetailModel
import com.rg.musiound.model.ActivitySongListModel
import com.rg.musiound.util.SongCallback
import com.rg.musiound.util.SongListCallback

/**
 * Create by roger
 * on 2019/8/16
 */
class ActivitySongListPresenter : BasePresenter<IActivitySongListView, IActivitySongListModel>(), IActivitySongListPresenter {
    override fun onSongListLoad(songList: List<SongList>) {
        view?.setSongList(songList)
    }

    override fun attachModel(): IActivitySongListModel {
        return ActivitySongListModel()
    }



    override fun onDataNotAvailable() {
        view?.showError()
    }

    override fun populateData() {

        model?.getData(object : SongListCallback {
            override fun onSuccess(song: List<SongList>) {
                onSongListLoad(song)
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
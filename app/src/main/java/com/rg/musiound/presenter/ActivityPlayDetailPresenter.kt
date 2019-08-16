package com.rg.musiound.presenter

import android.util.Log
import com.rg.musiound.base.BasePresenter
import com.rg.musiound.bean.Song
import com.rg.musiound.interfaces.model.IActivityPlayDetailModel
import com.rg.musiound.interfaces.presenter.IActivityPlayDetailPresenter
import com.rg.musiound.interfaces.view.IActivityPlayDetailView
import com.rg.musiound.model.ActivityPlayDetailModel
import com.rg.musiound.util.SongCallback

/**
 * Create by roger
 * on 2019/8/16
 */
class ActivityPlayDetailPresenter : BasePresenter<IActivityPlayDetailView, IActivityPlayDetailModel>(),
    IActivityPlayDetailPresenter {
    override fun attachModel(): IActivityPlayDetailModel {
        return ActivityPlayDetailModel()
    }

    override fun onSongLoad(song: Song) {
        view?.setSong(song)
    }

    override fun onDataNotAvailable() {
        view?.showError()
    }

    override fun populateData() {

        model?.getData(object : SongCallback {
            override fun onSuccess(song: Song) {
                onSongLoad(song)
            }

            override fun onFailed() {
                onDataNotAvailable()
            }

        })
    }

    override fun start() {
        Log.d("roger", "populate")

        populateData()
    }
}
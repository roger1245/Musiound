package com.rg.musiound.presenter

import android.util.Log
import com.rg.musiound.base.BasePresenter
import com.rg.musiound.bean.MVDetailRaw
import com.rg.musiound.interfaces.model.IActivityMVPlayerModel
import com.rg.musiound.interfaces.presenter.IActivityMVPlayerPresenter
import com.rg.musiound.interfaces.view.IActivityMVPlayerView
import com.rg.musiound.model.ActivityMVPlayerModel
import com.rg.musiound.util.GenericCallback
import com.rg.musiound.util.GenericPageCallback

/**
 * Create by roger
 * on 2019/8/18
 */
class ActivityMVPlayerPresenter <T1, T2> : BasePresenter<IActivityMVPlayerView<T1, T2>, IActivityMVPlayerModel<T1, T2>>(), IActivityMVPlayerPresenter<T1, T2> {

    override fun onLoadMoreData(t: T2, page: Int) {
        view?.onLoadMoreData(t,page)
    }

    override fun loadMoreData(id: Long, page: Int) {
        model?.getMVComment(id, object: GenericPageCallback<T2> {
            override fun onSuccess(data: T2, page: Int) {
                onLoadMoreData(data, page)
            }

            override fun onFailed() {
                onDataNotAvailable()
            }
        }, page)
    }

    override fun onMVDetail(t: T1) {
        view?.setMVDetail(t)
    }

    override fun attachModel():  IActivityMVPlayerModel<T1, T2>{
        return  ActivityMVPlayerModel<T1, T2>()
    }



    override fun onDataNotAvailable() {
        view?.showError()
    }

    override fun populateData(id: Long) {
        model?.getMVComment(id, object: GenericPageCallback<T2> {
            override fun onSuccess(data: T2, page:Int) {
                onLoadMoreData(data)
            }

            override fun onFailed() {
                onDataNotAvailable()
            }
        })
        model?.getMVDetail(id, object: GenericCallback<T1> {
            override fun onSuccess(data: T1) {
                onMVDetail(data)
            }

            override fun onFailed() {
                onDataNotAvailable()
            }
        })
    }

    override fun start(id: Long) {
        populateData(id)
    }


}
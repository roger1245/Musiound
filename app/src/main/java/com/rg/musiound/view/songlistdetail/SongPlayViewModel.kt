package com.rg.musiound.view.songlistdetail

import com.rg.musiound.bean.songdetail.SongDetailRoot
import com.rg.musiound.interfaces.network.ListDetailService
import com.rg.musiound.model.safeSubscribeBy
import com.rg.musiound.util.network.APIGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SongPlayViewModel {

    companion object {
        fun getSongDetail(id: String, callback: (SongDetailRoot) -> Unit) {
            APIGenerator.getApiService(ListDetailService::class.java)
                .getSongDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribeBy(
                    onError = {

                    },
                    onComplete = {

                    },
                    onNext = {
                        callback.invoke(it)
                    }
                )
        }
    }
}
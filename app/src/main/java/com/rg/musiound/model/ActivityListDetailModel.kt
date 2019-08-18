package com.rg.musiound.model

import com.rg.musiound.base.BaseModel
import com.rg.musiound.interfaces.model.IActivityListDetailModel
import com.rg.musiound.interfaces.network.ListDetailService
import com.rg.musiound.util.SongsCallback
import com.rg.musiound.util.network.APIGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Create by roger
 * on 2019/8/17
 */
class ActivityListDetailModel : BaseModel(), IActivityListDetailModel {
    override fun getData(callback: SongsCallback, songList: Long) {
        APIGenerator.getApiService(ListDetailService::class.java)
            .getListDetail(songList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    callback.onFailed()

                },
                onComplete = {

                },
                onNext = {
                    callback.onSuccess(it)

                }
            )
    }
}
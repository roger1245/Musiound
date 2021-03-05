package com.rg.musiound.view.songlistdetail

import android.util.Log
import com.rg.musiound.base.BaseModel
import com.rg.musiound.interfaces.model.IActivityListDetailModel
import com.rg.musiound.interfaces.network.ListDetailService
import com.rg.musiound.model.safeSubscribeBy
import com.rg.musiound.util.SongsCallback
import com.rg.musiound.util.network.APIGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Create by roger
 * on 2019/8/17
 */
class ActivityListDetailModel : BaseModel(), IActivityListDetailModel {
    override fun getData(callback: SongsCallback, id: Long) {
        APIGenerator.getApiService(ListDetailService::class.java)
            .getListDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    Log.d("roger", it.toString())
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
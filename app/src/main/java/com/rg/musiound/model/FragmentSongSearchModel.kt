package com.rg.musiound.model

import android.util.Log
import com.rg.musiound.base.BaseModel
import com.rg.musiound.interfaces.generic.IActivityGenericModel
import com.rg.musiound.interfaces.network.SearchService
import com.rg.musiound.util.GenericCallback
import com.rg.musiound.util.network.APIGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Create by roger
 * on 2019/8/19
 */
class FragmentSongSearchModel<T, P> : IActivityGenericModel<T, P>, BaseModel() {
    override fun getData(uri: P, page: Int, callback: GenericCallback<T>) {
        APIGenerator.getApiService(SearchService::class.java)
            .getSong(uri as String, "song", page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    callback.onFailed()
                    Log.d("roger", "onError" + it.toString())

                },
                onComplete = {

                },
                onNext = {
                    callback.onSuccess(it as T)

                }
            )

    }
}
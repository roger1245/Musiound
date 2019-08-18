package com.rg.musiound.model

import android.util.Log
import com.rg.musiound.base.BaseModel
import com.rg.musiound.interfaces.model.IActivitySongListModel
import com.rg.musiound.interfaces.network.SongListService
import com.rg.musiound.util.SongListCallback
import com.rg.musiound.util.network.APIGenerator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Create by roger
 * on 2019/8/16
 */
fun <T> Observable<T>.safeSubscribeBy(
    onError: (Throwable) -> Unit = {},
    onComplete: () -> Unit = {},
    onNext: (T) -> Unit = {}
): Disposable = subscribe(onNext, onError, onComplete)

class ActivitySongListModel : BaseModel(), IActivitySongListModel {
    override fun getData(callback: SongListCallback) {
        APIGenerator.getApiService(SongListService::class.java)
            .getSongList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    callback.onFailed()
                    Log.d("roger", "onError"  + it.toString())

                },
                onComplete = {

                },
                onNext = {
                    callback.onSuccess(it.data)

                }
            )
    }
}

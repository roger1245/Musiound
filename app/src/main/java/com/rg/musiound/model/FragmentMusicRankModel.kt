package com.rg.musiound.model

import android.util.Log
import com.rg.musiound.base.BaseModel
import com.rg.musiound.base.IBaseModel
import com.rg.musiound.interfaces.generic.IActivityGenericModel
import com.rg.musiound.interfaces.model.IFragmentMusicRankModel
import com.rg.musiound.interfaces.network.MVListService
import com.rg.musiound.interfaces.network.SearchService
import com.rg.musiound.interfaces.network.SongListService
import com.rg.musiound.util.GenericCallback
import com.rg.musiound.util.MVListCallback
import com.rg.musiound.util.network.APIGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Create by roger
 * on 2019/8/18
 */
class FragmentMusicRankModel<T, P> : IActivityGenericModel<T, P>, BaseModel() {

//    override fun getData(callback: MVListCallback) {
//        APIGenerator.getApiService(MVListService::class.java)
//            .getMVList()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .safeSubscribeBy(
//                onError = {
//                    callback.onFailed()
//                    Log.d("roger", "onError"  + it.toString())
//
//                },
//                onComplete = {
//
//                },
//                onNext = {
//                    callback.onSuccess(it.data)
//
//                }
//            )
//    }
    override fun getData(uri: P, page: Int, callback: GenericCallback<T>) {
        APIGenerator.getApiService(MVListService::class.java)
            .getMVList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    callback.onFailed()
                    Log.d("roger2", "onError    " + it.toString())

                },
                onComplete = {

                },
                onNext = {
                    callback.onSuccess(it as T)

                }
            )
    }
}
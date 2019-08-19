package com.rg.musiound.model

import android.util.Log
import com.rg.musiound.base.BaseModel
import com.rg.musiound.bean.MVDetailRaw
import com.rg.musiound.interfaces.model.IActivityMVPlayerModel
import com.rg.musiound.interfaces.network.MVCommentService
import com.rg.musiound.interfaces.network.MVDetailService
import com.rg.musiound.util.GenericCallback
import com.rg.musiound.util.GenericPageCallback
import com.rg.musiound.util.network.APIGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Create by roger
 * on 2019/8/18
 */
class ActivityMVPlayerModel<T1, T2> : BaseModel(), IActivityMVPlayerModel<T1, T2> {

    override fun getMVDetail(id: Long, callback: GenericCallback<T1>) {
        APIGenerator.getApiService(MVDetailService::class.java)
            .getMVDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    callback.onFailed()

                },
                onComplete = {

                },
                onNext = {
                    callback.onSuccess(it as T1)

                }
            )
    }

    override fun getMVComment(id: Long, callback: GenericPageCallback<T2>, page: Int) {
        APIGenerator.getApiService(MVCommentService::class.java)
            .getMVComment(id, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    callback.onFailed()
                },
                onComplete = {

                },
                onNext = {
                    callback.onSuccess(it as T2, page)


                }
            )
    }
}
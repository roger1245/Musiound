package com.rg.musiound.model

import android.util.Log
import com.rg.musiound.base.BaseModel
import com.rg.musiound.bean.SongListRaw
import com.rg.musiound.interfaces.model.IFragmentSongListModel
import com.rg.musiound.interfaces.network.SongListService
import com.rg.musiound.util.GenericPageCallback
import com.rg.musiound.util.network.APIGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Create by roger
 * on 2019/8/19
 */
class FragmentSongListModel<T>: BaseModel(), IFragmentSongListModel<T> {
    override fun getData(callback: GenericPageCallback<T>, page: Int, category: String) {
        APIGenerator.getApiService(SongListService::class.java)
            .getSongList(category, page)
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
                    callback.onSuccess(it as T)

                }
            )
    }
}
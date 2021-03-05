package com.rg.musiound.view.songlist

import android.util.Log
import com.rg.musiound.base.BaseModel
import com.rg.musiound.interfaces.model.IFragmentSongListModel
import com.rg.musiound.interfaces.network.SongListService
import com.rg.musiound.model.safeSubscribeBy
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
            .getSongList(offset = ((page) * 50).toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    Log.d("roger", "wrong :" +  it.toString())

                },
                onComplete = {
                },
                onNext = {
                    Log.d("roger", it.toString())

                    callback.onSuccess(it as T, page)

                }
            )
    }


}
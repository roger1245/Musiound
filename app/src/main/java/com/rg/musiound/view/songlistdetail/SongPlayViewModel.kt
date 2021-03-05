package com.rg.musiound.view.songlistdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rg.musiound.bean.songurl.SongUrl
import com.rg.musiound.interfaces.network.ListDetailService
import com.rg.musiound.model.safeSubscribeBy
import com.rg.musiound.util.network.APIGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SongPlayViewModel : ViewModel() {
    val songDetailRoot: MutableLiveData<SongUrl> = MutableLiveData()

    fun getSongUrl(ids: List<String>) {
        APIGenerator.getApiService(ListDetailService::class.java)
            .getSongUrl(ids)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {

                },
                onComplete = {

                },
                onNext = {
                    songDetailRoot.value = it
                }
            )
    }
}
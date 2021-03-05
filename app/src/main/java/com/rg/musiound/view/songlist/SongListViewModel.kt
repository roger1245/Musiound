//package com.rg.musiound.view.songlist
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.rg.musiound.bean.songlist.SongListRoot
//import com.rg.musiound.bean.songlistdetail.SongListDetailRoot
//import com.rg.musiound.interfaces.network.SongListService
//import com.rg.musiound.model.safeSubscribeBy
//import com.rg.musiound.util.GenericPageCallback
//import com.rg.musiound.util.network.APIGenerator
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//
//class SongListViewModel : ViewModel() {
//    private val hotSongLists = MutableLiveData<SongListRoot>()
//    fun getHotSongLists() {
//        val category = "hot"
//        APIGenerator.getApiService(SongListService::class.java)
//            .getSongList(category)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .safeSubscribeBy{
//                hotSongLists.value = it
//            }
//    }
//
//    fun getSongListDetail(id: Int, callback: (SongListDetailRoot) -> Void)
//    {
//        APIGenerator.getApiService(SongListService::class.java)
//            .getSongListDetail(id)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .safeSubscribeBy {
//                callback.invoke(it)
//            }
//    }
//
//}
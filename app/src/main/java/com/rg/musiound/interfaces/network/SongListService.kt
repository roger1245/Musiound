package com.rg.musiound.interfaces.network

import com.rg.musiound.bean.SongListRaw
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Create by roger
 * on 2019/8/16
 */
interface SongListService {
    @GET("songList/highQuality?cat=全部&pageSize=50")
    fun getSongList(): Observable<SongListRaw>
}
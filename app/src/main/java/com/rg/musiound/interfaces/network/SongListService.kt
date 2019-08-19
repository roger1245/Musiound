package com.rg.musiound.interfaces.network

import com.rg.musiound.bean.SongListRaw
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Create by roger
 * on 2019/8/16
 */
interface SongListService {
    @GET("songList/" + "{category}" +  "?cat=全部&pageSize=30")
    fun getSongList(@Path("category") category: String = "highQuality", @Query("page") page: Int = 0): Observable<SongListRaw>
}
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
    @GET("songList/" + "{category}")
    fun getSongList(@Path("category") category: String, @Query("page") page: Int , @Query("cat") cat: String = "全部", @Query("pageSize") pageSize: Int = 30): Observable<SongListRaw>
}
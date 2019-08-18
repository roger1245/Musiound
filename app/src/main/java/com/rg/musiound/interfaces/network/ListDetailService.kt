package com.rg.musiound.interfaces.network

import com.rg.musiound.bean.ListDetailRaw
import com.rg.musiound.bean.SongListRaw
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Create by roger
 * on 2019/8/17
 */
interface ListDetailService {
    @GET("songList")
    fun getListDetail(@Query("id") id: Long): Observable<ListDetailRaw>
}
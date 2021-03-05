package com.rg.musiound.interfaces.network

import com.rg.musiound.bean.ListDetailRaw
import com.rg.musiound.bean.SongListRaw
import com.rg.musiound.bean.songdetail.SongDetailRoot
import com.rg.musiound.bean.songlistdetail.SongListDetailRoot
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Create by roger
 * on 2019/8/17
 */
interface ListDetailService {
    @GET("/playlist/detail")
    fun getListDetail(@Query("id") id: Long): Observable<SongListDetailRoot>

    @GET("/song/detail")
    fun getSongDetail(@Query("ids") id: String): Observable<SongDetailRoot>
}
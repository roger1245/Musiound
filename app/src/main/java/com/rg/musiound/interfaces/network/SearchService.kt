package com.rg.musiound.interfaces.network

import com.rg.musiound.bean.MVListRaw
import com.rg.musiound.bean.SongListSearchRaw
import com.rg.musiound.bean.SongSearchRaw
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Create by roger
 * on 2019/8/19
 */
interface SearchService {
    @GET("search")
    fun getSongList(@Query("keyword") keyword: String, @Query("type") type: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20): Observable<SongListSearchRaw>
    @GET("search")
    fun getSong(@Query("keyword") keyword: String, @Query("type") type: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int = 20): Observable<SongSearchRaw>
}
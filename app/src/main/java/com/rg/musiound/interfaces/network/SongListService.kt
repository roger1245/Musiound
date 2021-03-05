package com.rg.musiound.interfaces.network

import com.rg.musiound.bean.songlist.SongList
import com.rg.musiound.bean.songlistdetail.SongListDetailRoot
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Create by roger
 * on 2019/8/16
 */
interface SongListService {
//    @GET("/playlist/" + "{category}")
//    fun getSongList(
//        @Path("category") category: String,
//        @Query("page") page: Int = 0,
//        @Query("cat") cat: String = "全部",
//        @Query("pageSize") pageSize: Int = 30
//    ): Observable<SongListRoot>

    @GET("/top/playlist")
    fun getSongList(
        @Query("order") order: String = "",
        @Query("cat") cat: String = "",
        @Query("limit") limit: String = "50",
        @Query("offset") offset: String = "0"
    ): Observable<SongList>

    @GET("/playlist/detail")
    fun getSongListDetail(@Query("id") id: Int): Observable<SongListDetailRoot>
}
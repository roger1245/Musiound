package com.rg.musiound.interfaces.network

import com.rg.musiound.bean.MVListRaw
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Create by roger
 * on 2019/8/18
 */
interface MVListService {
    @GET("mv/top")
    fun getMVList(@Query("page")page: Int = 0, @Query("pageSize")pageSize: Int = 10): Observable<MVListRaw>
}
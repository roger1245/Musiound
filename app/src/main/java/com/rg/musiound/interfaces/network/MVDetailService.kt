package com.rg.musiound.interfaces.network

import com.rg.musiound.bean.MVDetailRaw
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Create by roger
 * on 2019/8/18
 */
interface MVDetailService {
    @GET("mv")
    fun getMVDetail(@Query("id") id: Long): Observable<MVDetailRaw>
}
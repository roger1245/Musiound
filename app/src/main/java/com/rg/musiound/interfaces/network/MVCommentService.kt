package com.rg.musiound.interfaces.network

import com.rg.musiound.bean.MVCommentRaw
import com.rg.musiound.bean.MVDetailRaw
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Create by roger
 * on 2019/8/18
 */
interface MVCommentService {
    @GET("comment/mv")
    fun getMVComment(@Query("id") id: Long, @Query("page") page: Int = 0, @Query("pageSize") pageSize: Int = 30 ): Observable<MVCommentRaw>
}
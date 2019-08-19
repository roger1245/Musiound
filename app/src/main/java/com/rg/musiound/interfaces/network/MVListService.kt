package com.rg.musiound.interfaces.network

import com.rg.musiound.bean.MVListRaw
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Create by roger
 * on 2019/8/18
 */
interface MVListService {
    @GET("mv/top?pageSize=10&page=0")
    fun getMVList(): Observable<MVListRaw>
}
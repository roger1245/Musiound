package com.rg.musiound.util.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Create by roger
 * on 2019/8/16
 */
object APIGenerator {
    private var retrofit: Retrofit
    private var okHttpClient: OkHttpClient
    private const val TIME_OUT = 100L

    init {
        okHttpClient = OkHttpClient.Builder().connectTimeout(TIME_OUT, TimeUnit.SECONDS).build()
        retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    fun <T> getApiService(clazz: Class<T>) = retrofit.create(clazz)

    fun <T> getApiService(retrofit: Retrofit, clazz: Class<T>) = retrofit.create(clazz)
}
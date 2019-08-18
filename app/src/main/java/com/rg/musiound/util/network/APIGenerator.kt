package com.rg.musiound.util.network

import android.content.Context
import android.net.ConnectivityManager
import com.rg.musiound.BaseApp
import com.rg.musiound.BuildConfig
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
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
    private const val TIME_OUT = 100

    init {
        okHttpClient = configureOkHttp(OkHttpClient.Builder())
        retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    private fun configureOkHttp(builder: OkHttpClient.Builder): OkHttpClient {
        builder.connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor {
                val connectivityManager = BaseApp.context.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager
                val networkInfo = connectivityManager.activeNetworkInfo
                var response: Response
                if (networkInfo == null || !networkInfo.isConnected) {
                    val request = it.request().newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                    response = it.proceed(request)
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=${Int.MAX_VALUE}")
                        .build()
                } else {
                    response = it.proceed(it.request())
                }
                response
            }
            .addNetworkInterceptor {
                val response = it.proceed(it.request())
                response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=60")
                    .build()
            }
            .cache(Cache(BaseApp.context.externalCacheDir.absoluteFile, 10*1024*1024))

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        return builder.build()
    }
    fun <T> getApiService(clazz: Class<T>) = retrofit.create(clazz)

    fun <T> getApiService(retrofit: Retrofit, clazz: Class<T>) = retrofit.create(clazz)
}
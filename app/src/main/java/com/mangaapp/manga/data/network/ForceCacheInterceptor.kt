package com.mangaapp.manga.data.network

import android.content.Context
import com.mangaapp.manga.util.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * An OkHttp Interceptor that forces the use of the cache when the device is offline.
 */
class ForceCacheInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        if (!NetworkUtils.isInternetAvailable(context)) {
            builder.cacheControl(CacheControl.FORCE_CACHE);
        }
        return chain.proceed(builder.build());
    }
}
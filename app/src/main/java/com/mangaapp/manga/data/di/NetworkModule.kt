package com.mangaapp.manga.data.di

import android.content.Context
import com.google.gson.Gson
import com.mangaapp.manga.data.network.ApiService
import com.mangaapp.manga.data.network.CacheInterceptor
import com.mangaapp.manga.data.network.ForceCacheInterceptor
import com.mangaapp.manga.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    /**
     * Provides a singleton instance of Gson.
     */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    /**
     * Provides a configured Retrofit instance for making network requests.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /**
     * Provides a singleton instance of OkHttpClient configured with caching and interceptors.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {

        //for printing HTTP logs
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .cache(Cache(File(context.cacheDir, "http-cache"), 10L * 1024L * 1024L))
            .addNetworkInterceptor(CacheInterceptor())
            .addInterceptor(ForceCacheInterceptor(context))
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Provides an instance of the [ApiService] service.
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
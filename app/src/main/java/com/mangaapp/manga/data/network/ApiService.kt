package com.mangaapp.manga.data.network

import com.mangaapp.BuildConfig
import com.mangaapp.manga.data.dto.MangaResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Interface defining the API service for fetching API Data.
 */
interface ApiService {

    @GET("fetch")
    suspend fun getMangaList(
        @Query("page") page: Int,
        @Header("x-rapidapi-host") host: String = BuildConfig.HOST,
        @Header("x-rapidapi-key") apiKey: String = BuildConfig.API_KEY
    ): Response<MangaResponseDTO>
}
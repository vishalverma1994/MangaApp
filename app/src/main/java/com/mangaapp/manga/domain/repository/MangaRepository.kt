package com.mangaapp.manga.domain.repository

import androidx.paging.PagingSource
import com.mangaapp.manga.data.local.MangaEntity
import com.mangaapp.manga.domain.model.Manga
import com.mangaapp.manga.util.Either
import com.mangaapp.manga.util.NetworkError

/**
 * Interface for managing manga data, providing access to both remote and local data sources.
 */
interface MangaRepository {

    suspend fun fetchMangaFromAPI(page: Int) : Either<NetworkError, List<Manga>>
    suspend fun insertMangaIntoDB(mangaList: List<MangaEntity>)
    fun getMangaFromLocal(): PagingSource<Int, MangaEntity>
    suspend fun getMangaFromLocalById(id: String): Manga?
    suspend fun clearMangaDB()
}
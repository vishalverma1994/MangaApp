package com.mangaapp.manga.domain.usecases

import androidx.paging.PagingSource
import com.mangaapp.manga.data.local.MangaEntity
import com.mangaapp.manga.domain.model.Manga
import com.mangaapp.manga.domain.repository.MangaRepository
import javax.inject.Inject

/**
 * `FetchMangaDBUseCase` class responsible for fetching manga data from the local database.
 */
class FetchMangaDBUseCase @Inject constructor(private val mangaRepository: MangaRepository) {

    fun getMangaFromLocal(): PagingSource<Int, MangaEntity> {
        return mangaRepository.getMangaFromLocal()
    }

    suspend fun getMangaFromLocalById(id: String): Manga? {
        return mangaRepository.getMangaFromLocalById(id)
    }
}
package com.mangaapp.manga.domain.usecases

import com.mangaapp.manga.domain.repository.MangaRepository
import javax.inject.Inject

/**
 * `ClearMangaDBUseCase` responsible for clearing the entire manga database.
 */
class ClearMangaDBUseCase @Inject constructor(private val mangaRepository: MangaRepository) {

    suspend operator fun invoke() {
        mangaRepository.clearMangaDB()
    }
}
package com.mangaapp.manga.domain.usecases

import com.mangaapp.manga.domain.model.Manga
import com.mangaapp.manga.domain.repository.MangaRepository
import com.mangaapp.manga.util.Either
import com.mangaapp.manga.util.NetworkError
import javax.inject.Inject

/**
 * `FetchMangaUseCase` responsible for fetching a list of manga from the repository.
 */
class FetchMangaUseCase @Inject constructor(private val mangaRepository: MangaRepository) {

    suspend operator fun invoke(page: Int): Either<NetworkError, List<Manga>> {
        return mangaRepository.fetchMangaFromAPI(page = page)
    }
}
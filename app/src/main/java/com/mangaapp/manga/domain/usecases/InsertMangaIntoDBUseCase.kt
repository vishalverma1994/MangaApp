package com.mangaapp.manga.domain.usecases

import com.mangaapp.manga.data.local.MangaEntity
import com.mangaapp.manga.domain.repository.MangaRepository
import javax.inject.Inject

/**
 * `InsertMangaIntoDBUseCase` responsible for inserting a list of MangaEntity objects into the database.
 */
class InsertMangaIntoDBUseCase @Inject constructor(private val repository: MangaRepository) {

    suspend operator fun invoke(mangaList: List<MangaEntity>) {
        repository.insertMangaIntoDB(mangaList)
    }
}
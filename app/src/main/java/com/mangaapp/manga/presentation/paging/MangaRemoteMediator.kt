package com.mangaapp.manga.presentation.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.mangaapp.manga.data.local.MangaEntity
import com.mangaapp.manga.data.local.toMangaEntity
import com.mangaapp.manga.domain.model.Manga
import com.mangaapp.manga.domain.usecases.ClearMangaDBUseCase
import com.mangaapp.manga.domain.usecases.FetchMangaUseCase
import com.mangaapp.manga.domain.usecases.InsertMangaIntoDBUseCase
import com.mangaapp.manga.util.Either
import com.mangaapp.manga.util.NetworkError
import okhttp3.internal.toImmutableList

/**
 * [MangaRemoteMediator] is responsible for loading manga data from the network and caching it in the local database.
 * It implements [RemoteMediator] to handle pagination of the manga list.
 */
@OptIn(ExperimentalPagingApi::class)
class MangaRemoteMediator(
    private val insertMangaIntoDBUseCase: InsertMangaIntoDBUseCase,
    private val clearMangaDBUseCase: ClearMangaDBUseCase,
    private val fetchMangaUseCase: FetchMangaUseCase
) : RemoteMediator<Int, MangaEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MangaEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                clearMangaDBUseCase.invoke()
                1
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val loadedItems = state.pages.sumOf { it.data.size }
                val nextPage = (loadedItems / state.config.pageSize) + 1
                nextPage
            }
        }

        return try {
            val result: Either<NetworkError, List<Manga>> = fetchMangaUseCase.invoke(page)
            when(result) {
                is Either.Failure -> {
                    MediatorResult.Error(Throwable(result.error.toString()))
                }
                is Either.Success -> {
                    val mangaList = result.data.toImmutableList().map { it.toMangaEntity(page) }
                    insertMangaIntoDBUseCase.invoke(mangaList)
                    MediatorResult.Success(endOfPaginationReached = mangaList.isEmpty())
                }
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
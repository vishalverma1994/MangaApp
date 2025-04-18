package com.mangaapp.manga.data.repository

import android.util.Log
import androidx.paging.PagingSource
import com.mangaapp.manga.data.dto.toManga
import com.mangaapp.manga.data.local.MangaDao
import com.mangaapp.manga.data.local.MangaEntity
import com.mangaapp.manga.data.local.SessionManagerDataStore
import com.mangaapp.manga.data.local.UserDao
import com.mangaapp.manga.data.local.UserEntity
import com.mangaapp.manga.data.local.toManga
import com.mangaapp.manga.data.network.ApiService
import com.mangaapp.manga.domain.model.Manga
import com.mangaapp.manga.domain.repository.MangaRepository
import com.mangaapp.manga.util.Either
import com.mangaapp.manga.util.NetworkError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException


/**
 * Implementation of the [MangaRepository] interface.
 *
 * This class handles fetching manga data from both the network and local database,
 * as well as user authentication and session management.
 */
data class MangaRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val mangaDao: MangaDao,
    private val ioCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): MangaRepository {

    override suspend fun fetchMangaFromAPI(page: Int): Either<NetworkError, List<Manga>> {
        return withContext(ioCoroutineDispatcher) {
            return@withContext try {
                val response = apiService.getMangaList(page = page)
                if (response.isSuccessful) {
                    val body = response.body()
                    return@withContext if (body != null && body.code == 200 && body.data != null) {
                        if (body.data.isEmpty()) {
                            Either.Failure(NetworkError.EmptyDataFound)
                        } else {
                            val mangaList = body.data.map { it.toManga() }
                            Either.Success(data = mangaList)
                        }
                    } else Either.Failure(NetworkError.NoBodyFound)
                } else Either.Failure(NetworkError.NoBodyFound)

            } catch (e: Exception) {
                when(e) {
                    is CancellationException -> throw e
                    is HttpException -> {
                        return@withContext Either.Failure(NetworkError.ServerError(e.code(), e.message ?: ""))
                    }

                    is IOException -> {
                        return@withContext Either.Failure(NetworkError.NoInternet)
                    }

                    else -> {
                        return@withContext Either.Failure(
                            NetworkError.UnknownError(
                                e.message ?: "no message found"
                            )
                        )
                    }
                }
            }
        }
    }

    override suspend fun insertMangaIntoDB(mangaList: List<MangaEntity>) {
        mangaDao.insertManga(mangaList)
    }

    override fun getMangaFromLocal(): PagingSource<Int, MangaEntity> {
        return mangaDao.getMangaPagingSource()
    }

    override suspend fun getMangaFromLocalById(id: String): Manga? = mangaDao.getMangaById(id)?.toManga()

    override suspend fun clearMangaDB() {
        mangaDao.clearManga()
    }
}
package com.mangaapp.manga.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mangaapp.manga.domain.model.Manga
import com.mangaapp.manga.domain.usecases.ClearMangaDBUseCase
import com.mangaapp.manga.domain.usecases.FetchMangaDBUseCase
import com.mangaapp.manga.domain.usecases.FetchMangaUseCase
import com.mangaapp.manga.domain.usecases.InsertMangaIntoDBUseCase
import com.mangaapp.manga.presentation.paging.MangaRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [MangaViewModel] is a ViewModel class responsible for managing and providing Manga data
 * to the UI. It interacts with use cases for fetching, inserting, and clearing Manga data,
 * both from the network and the local database.
 */
@HiltViewModel
class MangaViewModel @Inject constructor(
    private val insertMangaIntoDBUseCase: InsertMangaIntoDBUseCase,
    private val clearMangaDBUseCase: ClearMangaDBUseCase,
    private val fetchMangaUseCase: FetchMangaUseCase,
    private  val fetchMangaDBUseCase: FetchMangaDBUseCase
): ViewModel() {

    private val TAG = "MangaViewModel"

    private val _manga = MutableStateFlow<Manga?>(null)
    val manga: StateFlow<Manga?> = _manga.asStateFlow()

    @OptIn(ExperimentalPagingApi::class)
    val mangaPagingFlow = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        remoteMediator = MangaRemoteMediator(insertMangaIntoDBUseCase, clearMangaDBUseCase, fetchMangaUseCase),
        pagingSourceFactory = { fetchMangaDBUseCase.getMangaFromLocal() }
    ).flow.cachedIn(viewModelScope)

    fun getMangaFromLocalById(mangaId: String) {
        viewModelScope.launch {
            _manga.value = fetchMangaDBUseCase.getMangaFromLocalById(mangaId)
        }
    }
}
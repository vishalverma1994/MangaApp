package com.mangaapp.manga.domain.di

import com.mangaapp.manga.domain.repository.MangaRepository
import com.mangaapp.manga.domain.repository.UserRepository
import com.mangaapp.manga.domain.usecases.CheckUserLoggedInUseCase
import com.mangaapp.manga.domain.usecases.ClearMangaDBUseCase
import com.mangaapp.manga.domain.usecases.EmailSignInUseCase
import com.mangaapp.manga.domain.usecases.FetchMangaDBUseCase
import com.mangaapp.manga.domain.usecases.FetchMangaUseCase
import com.mangaapp.manga.domain.usecases.InsertMangaIntoDBUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * `UseCaseModule` is a Dagger Hilt module that provides dependencies for various use case classes.
 */
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideCheckUserLoggedInUseCase(userRepository: UserRepository): CheckUserLoggedInUseCase {
        return CheckUserLoggedInUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideEmailSignInUseCase(userRepository: UserRepository): EmailSignInUseCase {
        return EmailSignInUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideFetchMangaUseCase(mangaRepository: MangaRepository): FetchMangaUseCase {
        return FetchMangaUseCase(mangaRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideFetchMangaDBUseCase(mangaRepository: MangaRepository): FetchMangaDBUseCase {
        return FetchMangaDBUseCase(mangaRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideInsertMangaInDBUseCase(mangaRepository: MangaRepository): InsertMangaIntoDBUseCase {
        return InsertMangaIntoDBUseCase(mangaRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideClearMangaDBUseCase(mangaRepository: MangaRepository): ClearMangaDBUseCase {
        return ClearMangaDBUseCase(mangaRepository)
    }

}
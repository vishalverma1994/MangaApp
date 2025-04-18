package com.mangaapp.manga.data.di

import com.mangaapp.manga.data.local.MangaDao
import com.mangaapp.manga.data.local.SessionManagerDataStore
import com.mangaapp.manga.data.local.UserDao
import com.mangaapp.manga.data.network.ApiService
import com.mangaapp.manga.data.repository.MangaRepoImpl
import com.mangaapp.manga.data.repository.UserRepoImpl
import com.mangaapp.manga.domain.repository.MangaRepository
import com.mangaapp.manga.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class RepoModule {

    /**
     * Provides an instance of [UserRepository] using [UserRepoImpl] as the concrete implementation.
     */
    @Provides
    @ViewModelScoped
    fun provideUserRepoImpl(apiService: ApiService, userDao: UserDao, sessionManagerDataStore: SessionManagerDataStore): UserRepository{
        return UserRepoImpl(apiService= apiService, userDao = userDao, sessionManagerDataStore = sessionManagerDataStore)
    }

    /**
     * Provides an instance of [MangaRepository] using [MangaRepoImpl] as the concrete implementation.
     */
    @Provides
    @ViewModelScoped
    fun provideMangaRepoImpl(apiService: ApiService, mangaDao: MangaDao):MangaRepository{
        return MangaRepoImpl(apiService= apiService, mangaDao = mangaDao)
    }
}
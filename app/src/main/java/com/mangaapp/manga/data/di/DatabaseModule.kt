package com.mangaapp.manga.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.mangaapp.manga.data.local.MangaDatabase
import com.mangaapp.manga.util.MANGA_DATASTORE_NAME
import com.mangaapp.manga.util.MANGA_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides a singleton instance of the [MangaDatabase] using Room.
     */
    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app, MangaDatabase::class.java, MANGA_DB_NAME
    ).build()

    /**
     * Provides an instance of the [MangaDao] interface.
     */
    @Singleton
    @Provides
    fun provideMangaDao(db: MangaDatabase) = db.mangaDao()

    /**
     * Provides an instance of the [UserDao] interface.
     */
    @Singleton
    @Provides
    fun provideUserDao(db: MangaDatabase) = db.userDao()

    /**
     * Provide an instance of [SessionManagerDataStore] DataStore
     */
    @Singleton
    @Provides
    fun provideExchangeRateDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(MANGA_DATASTORE_NAME) }
        )
}
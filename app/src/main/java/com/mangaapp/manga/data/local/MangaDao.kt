package com.mangaapp.manga.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object (DAO) interface for interacting with the MangaEntity table in the database.
 */
@Dao
interface MangaDao {

    @Query("SELECT * FROM manga")
    fun getMangaPagingSource(): PagingSource<Int, MangaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManga(manga: List<MangaEntity>)

    @Query("SELECT * FROM manga WHERE id = :id")
    suspend fun getMangaById(id: String): MangaEntity?

    @Query("DELETE FROM manga")
    suspend fun clearManga()
}
package com.mangaapp.manga.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mangaapp.manga.domain.model.Manga

/**
 * Represents a Manga entity in the local database.
 */
@Entity(tableName = "manga")
@TypeConverters(Converters::class)
data class MangaEntity(
     @PrimaryKey
     val id: String,
     val title: String,
     val subTitle: String,
     val status: String,
     val thumbnail: String,
     val summary: String,
     val authors: List<String>,
     val genres: List<String>,
     val nsfw: String,
     val type: String,
     val totalChapter: Int,
     val createdAt: Long,
     val updatedAt: Long,
     val page: Int? = null
)

fun Manga.toMangaEntity(page: Int): MangaEntity {
     return MangaEntity(
          id = id,
          title = title,
          subTitle = subTitle,
          status = status,
          thumbnail = thumbnail,
          summary = summary,
          authors = authors,
          genres = genres,
          nsfw = nsfw,
          type = type,
          totalChapter = totalChapter,
          createdAt = createdAt,
          updatedAt = updatedAt,
          page = page
     )
}

fun MangaEntity.toManga(): Manga {
     return Manga(
          id = id,
          title = title,
          subTitle = subTitle,
          status = status,
          thumbnail = thumbnail,
          summary = summary,
          authors = authors,
          genres = genres,
          nsfw = nsfw,
          type = type,
          totalChapter = totalChapter,
          createdAt = createdAt,
          updatedAt = updatedAt
     )
}


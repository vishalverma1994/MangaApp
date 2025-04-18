package com.mangaapp.manga.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.mangaapp.manga.domain.model.Manga

/**
 * Data Transfer Object (DTO) representing a manga.
 */
@Keep
data class MangaDTO(
    @SerializedName("id") val id: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("sub_title") val subTitle: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("thumb") val thumbnail: String? = null,
    @SerializedName("summary") val summary: String? = null,
    @SerializedName("authors") val authors: List<String> = arrayListOf(),
    @SerializedName("genres") val genres: List<String> = arrayListOf(),
    @SerializedName("nsfw") val nsfw: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("total_chapter") val totalChapter: Int? = null,
    @SerializedName("create_at") val createdAt: Long? = null,
    @SerializedName("update_at") val updatedAt: Long? = null
)

fun MangaDTO.toManga(): Manga {
    return Manga(
        id = id ?: "",
        title = title ?: "",
        subTitle = subTitle ?: "",
        status = status ?: "",
        thumbnail = thumbnail ?: "",
        summary = summary ?: "",
        authors = authors,
        genres = genres,
        nsfw = nsfw ?: "",
        type = type ?: "",
        totalChapter = totalChapter ?: 0,
        createdAt = createdAt ?: 0,
        updatedAt = updatedAt ?: 0
    )
}
package com.mangaapp.manga.domain.model

import com.google.gson.annotations.SerializedName

data class Manga(
    @SerializedName("id") val id: String = "",
    @SerializedName("title") val title: String,
    @SerializedName("sub_title") val subTitle: String,
    @SerializedName("status") val status: String,
    @SerializedName("thumb") val thumbnail: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("authors") val authors: List<String>,
    @SerializedName("genres") val genres: List<String>,
    @SerializedName("nsfw") val nsfw: String,
    @SerializedName("type") val type: String,
    @SerializedName("total_chapter") val totalChapter: Int,
    @SerializedName("create_at") val createdAt: Long,
    @SerializedName("update_at") val updatedAt: Long
)

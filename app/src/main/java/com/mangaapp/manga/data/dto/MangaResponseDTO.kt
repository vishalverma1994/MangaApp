package com.mangaapp.manga.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) representing the response from a manga-related API endpoint.
 */
@Keep
data class MangaResponseDTO(
    @SerializedName("code") val code: Int ? = null,
    @SerializedName("data") val data: List<MangaDTO>? = null
)

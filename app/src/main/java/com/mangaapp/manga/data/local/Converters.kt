package com.mangaapp.manga.data.local

import androidx.room.TypeConverter

/**
 * Converters class provides methods to convert complex data types
 */
class Converters {

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return if (data.isBlank()) emptyList() else data.split(",")
    }
}
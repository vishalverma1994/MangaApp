package com.mangaapp.manga.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a user in the application's database.
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val password: String
)

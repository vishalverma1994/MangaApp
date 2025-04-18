package com.mangaapp.manga.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * MangaDatabase class represents the Room database for the Manga application.
 * It defines the database schema, including the entities and version.
 * It also provides access to the Data Access Objects (DAOs) for interacting with the database.
 */
@Database(entities = [UserEntity::class, MangaEntity::class], version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class MangaDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun mangaDao(): MangaDao
}
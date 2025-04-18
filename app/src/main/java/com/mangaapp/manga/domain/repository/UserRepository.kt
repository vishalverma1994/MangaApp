package com.mangaapp.manga.domain.repository

/**
 * Interface for managing user-related data and operations.
 */
interface UserRepository {

    suspend fun checkUserLoggedIn(): Boolean
    suspend fun signInOrRegister(email: String, password: String): Boolean
}
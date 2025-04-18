package com.mangaapp.manga.domain.usecases

import com.mangaapp.manga.domain.repository.UserRepository
import javax.inject.Inject

/**
 * `EmailSignInUseCase` handles the use case of signing in a user with their email and password.
 */
class EmailSignInUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(email: String, password: String): Boolean {
        return repository.signInOrRegister(email, password)
    }
}
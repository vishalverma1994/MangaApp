package com.mangaapp.manga.domain.usecases

import com.mangaapp.manga.domain.repository.UserRepository
import javax.inject.Inject

/**
 * `CheckUserLoggedInUseCase` responsible for checking if a user is currently logged in.
 */
class CheckUserLoggedInUseCase @Inject constructor(
    private val repository: UserRepository,
) {

    suspend operator fun invoke(): Boolean {
        return repository.checkUserLoggedIn()
    }
}
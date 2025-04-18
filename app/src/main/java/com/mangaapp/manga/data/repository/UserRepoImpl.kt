package com.mangaapp.manga.data.repository

import com.mangaapp.manga.data.local.SessionManagerDataStore
import com.mangaapp.manga.data.local.UserDao
import com.mangaapp.manga.data.local.UserEntity
import com.mangaapp.manga.data.network.ApiService
import com.mangaapp.manga.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Concrete implementation of the [UserRepository] interface.
 */
class UserRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val sessionManagerDataStore: SessionManagerDataStore,
    private val ioCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): UserRepository {

    override suspend fun checkUserLoggedIn(): Boolean {
        return sessionManagerDataStore.getLoginState()
    }

    override suspend fun signInOrRegister(email: String, password: String): Boolean {
        val existing = userDao.getUserByEmail(email)
        return if (existing == null) {
            userDao.insertUser(UserEntity(email, password))
            sessionManagerDataStore.saveLoginState(true)
            true
        } else {
            val success = existing.password == password
            if (success) sessionManagerDataStore.saveLoginState(true)
            success
        }
    }
}
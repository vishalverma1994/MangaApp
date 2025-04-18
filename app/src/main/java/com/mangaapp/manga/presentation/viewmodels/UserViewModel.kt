package com.mangaapp.manga.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mangaapp.manga.domain.usecases.CheckUserLoggedInUseCase
import com.mangaapp.manga.domain.usecases.EmailSignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [UserViewModel] is a ViewModel responsible for managing user-related operations,
 * including checking user login status and handling email sign-in/registration.
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    private val checkUserLoggedInUseCase: CheckUserLoggedInUseCase,
    private val emailSignInUseCase: EmailSignInUseCase,
) : ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIn: StateFlow<Boolean?> = _isUserLoggedIn.asStateFlow()

    fun signInOrRegister(email: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isUserLoggedIn = emailSignInUseCase.invoke(email = email, password = password)
            _isUserLoggedIn.value = isUserLoggedIn
            callback(isUserLoggedIn)
        }
    }

    fun checkUserLoggedIn() {
        viewModelScope.launch {
            _isUserLoggedIn.value = checkUserLoggedInUseCase.invoke()
        }
    }
}
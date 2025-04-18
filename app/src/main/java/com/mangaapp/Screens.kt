package com.mangaapp

import com.mangaapp.manga.util.NavigationModeUtil
import kotlinx.serialization.Serializable


/**
 * Represents the route and state information for the sign-in screen.
 */
@Serializable
data class SignInRoute(
    val loggedIn: Boolean = false
)

/**
 * Represents the route for the home screen in the application.
 */
@Serializable
data class HomeRoute(
    val mode: String = NavigationModeUtil.MANGA.name,
    val id: String = ""
)
package com.mangaapp.manga.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mangaapp.HomeRoute
import com.mangaapp.manga.util.NavigationModeUtil

/**
 * A composable function that displays a bottom navigation bar.
 */
@Composable
fun BottomNavigationBar(currentRoute: String?, onNavigate: (HomeRoute) -> Unit) {
    val items = listOf(NavigationModeUtil.MANGA.name to Icons.Default.Book, NavigationModeUtil.FACE.name to Icons.Default.Face)
    NavigationBar {
        items.forEach { (route, icon) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = {
                    onNavigate(HomeRoute(route))
                },
                icon = {
                    Icon(imageVector = icon, contentDescription = route)
                },
                label = {
                    Text(route.replaceFirstChar { it.uppercase() })
                }
            )
        }
    }
}

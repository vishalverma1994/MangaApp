package com.mangaapp.manga.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mangaapp.HomeRoute
import com.mangaapp.manga.util.NavigationModeUtil

/**
 * A composable function that displays a bottom navigation bar.
 */
@Composable
fun BottomNavigationBar(currentRoute: String?, onNavigate: (HomeRoute) -> Unit) {
    val items =
        listOf(NavigationModeUtil.MANGA.name to Icons.Default.Book, NavigationModeUtil.FACE.name to Icons.Default.Face)
    NavigationBar(containerColor = Color.Black) {
        items.forEach { (route, icon) ->
            val selected = currentRoute == route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (selected.not()) onNavigate(HomeRoute(route))
                },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = route,
                        tint = if (selected) Color.White else Color.LightGray
                    )
                },
                label = {
                    Text(
                        text = route.replaceFirstChar { it.uppercase() },
                        color = if (selected) Color.White else Color.LightGray
                    )
                },
                alwaysShowLabel = true
            )
        }
    }
}

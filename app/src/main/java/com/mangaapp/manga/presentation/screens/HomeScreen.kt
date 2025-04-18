package com.mangaapp.manga.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mangaapp.HomeRoute
import com.mangaapp.manga.presentation.components.BottomNavigationBar
import com.mangaapp.manga.presentation.viewmodels.MangaViewModel
import com.mangaapp.manga.util.NavigationModeUtil

/**
 * [HomeScreen] is the main composable function that represents the different screens of the application.
 * It manages the navigation between the screens and displays the appropriate content based on the
 * provided parameters.
 */
@Composable
fun HomeScreen(
    currentRoute: String?,
    screen: String,
    mangaId: String = "",
    viewModel: MangaViewModel,
    onNavigate: (HomeRoute) -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(currentRoute = currentRoute, onNavigate = onNavigate)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (screen) {
                NavigationModeUtil.MANGA.name -> MangaScreen(onMangaClick = onNavigate)
                NavigationModeUtil.FACE.name -> FaceDetectionScreen()
                NavigationModeUtil.MANGA_DETAIL.name -> MangaDetailScreen(mangaId, viewModel)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
//    HomeScreen("", "", "", )
}
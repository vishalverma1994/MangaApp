package com.mangaapp.manga.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mangaapp.HomeRoute
import com.mangaapp.manga.domain.model.Manga
import com.mangaapp.manga.util.NavigationModeUtil

/**
 * Displays a single manga item in a grid format.
 */
@Composable
fun MangaGridItem(manga: Manga, onMangaClick: (HomeRoute) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onMangaClick(HomeRoute(mode = NavigationModeUtil.MANGA_DETAIL.name, id = manga.id))
            }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = manga.thumbnail,
                contentDescription = manga.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}
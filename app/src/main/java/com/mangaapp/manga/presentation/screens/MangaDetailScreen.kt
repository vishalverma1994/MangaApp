package com.mangaapp.manga.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mangaapp.manga.domain.model.Manga
import com.mangaapp.manga.presentation.viewmodels.MangaViewModel

/**
 * [MangaDetailScreen] is a composable function that displays the detailed information of a specific manga.
 * It fetches manga data using the provided [MangaViewModel] and displays it in a user-friendly layout.
 */
@Composable
fun MangaDetailScreen(id: String, viewModel: MangaViewModel) {
    val mangaState: State<Manga?> = viewModel.manga.collectAsState()
    val manga: Manga? = mangaState.value

    val isFavorite = remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        viewModel.getMangaFromLocalById(id)
    }

    manga?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {
                        isFavorite.value = !isFavorite.value
                        // logic to handle this with DB
                    }) {
                        Icon(
                            imageVector = if (isFavorite.value) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = if (isFavorite.value) "Unmark Favorite" else "Mark as Favorite",
                            tint = if (isFavorite.value) Color.Yellow else Color.Gray
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    AsyncImage(
                        model = it.thumbnail,
                        contentDescription = it.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(150.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = it.title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = it.subTitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.LightGray,
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Text(
                    text = it.summary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }
        }
    } ?: run {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text("Manga not found.", color = Color.White)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MangaDetailScreenPreview() {
//    MangaDetailScreen()
}
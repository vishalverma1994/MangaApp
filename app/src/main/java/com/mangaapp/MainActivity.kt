package com.mangaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mangaapp.manga.presentation.screens.HomeScreen
import com.mangaapp.manga.presentation.screens.SignInScreen
import com.mangaapp.manga.presentation.viewmodels.MangaViewModel
import com.mangaapp.manga.presentation.viewmodels.UserViewModel
import com.mangaapp.ui.theme.MangaAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MangaAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * This function sets up the navigation graph, handles user authentication state,
 * and displays the appropriate screens based on whether the user is logged in.
 */
@Composable
fun App(modifier: Modifier) {
    val userViewModel: UserViewModel = hiltViewModel()
    val navController = rememberNavController()
    LaunchedEffect(Unit) {
        userViewModel.checkUserLoggedIn()
    }
    val isLoggedInState = userViewModel.isUserLoggedIn.collectAsState()
    val isLoggedIn = isLoggedInState.value
    if (isLoggedIn == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val startDestination = if (isLoggedIn) HomeRoute() else SignInRoute()
        NavHost(navController = navController, startDestination = startDestination) {
            composable<SignInRoute> {
                SignInScreen {
                    navController.navigate(HomeRoute()) {
                        popUpTo(SignInRoute()) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
            composable<HomeRoute> { backStackEntry ->
                val viewModel: MangaViewModel = hiltViewModel(backStackEntry)
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                val homeRoute: HomeRoute = backStackEntry.toRoute()
                HomeScreen(
                    currentRoute = currentRoute,
                    screen = homeRoute.mode,
                    mangaId = homeRoute.id,
                    viewModel = viewModel
                ) { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App(modifier = Modifier)
}
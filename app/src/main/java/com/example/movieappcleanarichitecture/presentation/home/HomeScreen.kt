package com.example.movieappcleanarichitecture.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieappcleanarichitecture.presentation.drawer.NavigationDrawer
import com.example.movieappcleanarichitecture.presentation.movie_carousel.MovieCarouselViewModel
import com.example.movieappcleanarichitecture.presentation.movie_carousel.MovieCarouselWidget
import com.example.movieappcleanarichitecture.presentation.movie_tabbed.MovieTabbedWidget


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    movieCarouselViewModel: MovieCarouselViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawer(navHostController = navController)
        }) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.primary,
        ) {

            Column(
                modifier = Modifier
                    //                .padding(it)
                    .fillMaxSize()
            ) {
                MovieCarouselWidget(movieCarouselViewModel, drawerState, navController)
                MovieTabbedWidget(navController = navController)
            }
        }
    }
}
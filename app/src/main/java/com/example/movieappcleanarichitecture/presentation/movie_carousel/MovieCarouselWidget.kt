package com.example.movieappcleanarichitecture.presentation.movie_carousel

import CarouselMovie
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movieappcleanarichitecture.data.core.ApiConstants
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.ui.theme.AppColor

@Composable
fun MovieCarouselWidget(
    movieCarouselViewModel: MovieCarouselViewModel,
    drawerState: DrawerState,
    navController: NavHostController
) {
    val movies = movieCarouselViewModel.requestState.value.data?.toMutableList()
    var currentMovieState = remember {
        mutableStateOf(movies?.get(0))
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
    ) {
        if (movieCarouselViewModel.requestState.value.loading == false && currentMovieState.value != null) {
            MovieBackdropWidget(movie = currentMovieState.value!!)
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            MovieAppBar(drawerState, navController = navController)
            CarouselMovie(
                movieCarouselViewModel = movieCarouselViewModel,
                movies = movies,
                navController = navController
            ) {
                currentMovieState.value = it
            }
        }
    }
}





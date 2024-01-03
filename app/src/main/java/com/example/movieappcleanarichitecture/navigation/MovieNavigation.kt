package com.example.movieappcleanarichitecture.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappcleanarichitecture.presentation.favorite_movie.FavoriteScreen
import com.example.movieappcleanarichitecture.presentation.home.HomeScreen
import com.example.movieappcleanarichitecture.presentation.movie_detail.MovieDetailScreen
import com.example.movieappcleanarichitecture.presentation.search.SearchScreen
import com.example.movieappcleanarichitecture.presentation.watch_trailer.WatchTrailerScreen


@Composable
fun MovieNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MovieScreens.HOME.name) {
        composable(MovieScreens.HOME.name) {
            HomeScreen(navController = navController)
        }

        composable(
            MovieScreens.MOVIE_DETAIL.name+"/{movieId}",
            arguments = listOf(navArgument("movieId"){ type = NavType.IntType})
        ) {
            MovieDetailScreen(navController = navController, movieId = it.arguments?.getInt("movieId")!! )
        }

        composable(
            MovieScreens.WATCH_TRAILER.name+"/{movieId}",
            arguments = listOf(navArgument("movieId"){ type = NavType.IntType})
        ) {
            WatchTrailerScreen(movieId = it.arguments?.getInt("movieId")!!,navController = navController)
        }

        composable(MovieScreens.FAVORITE.name) {
            FavoriteScreen(navController = navController)
        }

        composable(MovieScreens.SEARCH_MOVIE.name) {
            SearchScreen(navController = navController)
        }
    }
}
package com.example.movieappcleanarichitecture.presentation.favorite_movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.movieappcleanarichitecture.components.ErrorWidget
import com.example.movieappcleanarichitecture.components.LoadingWidget
import com.example.movieappcleanarichitecture.data.core.ApiConstants
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.navigation.MovieScreens

@Composable
fun FavoriteScreen(
    favoriteMovieViewModel: FavoriteMovieViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state = favoriteMovieViewModel.state.value
    LaunchedEffect(true) {
        favoriteMovieViewModel.getFavoriteMovies()
    }

    when (state) {
        is FavoriteMovieViewModel.State.Loading -> LoadingWidget()
        is FavoriteMovieViewModel.State.Error -> ErrorWidget()
        is FavoriteMovieViewModel.State.Loaded ->
            Scaffold(
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Column {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(it),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back icon",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Text(
                            text = "Favorite Movies",
                            style = MaterialTheme.typography.titleMedium.copy(Color.White)
                        )
                    }

                    ListFavoriteMovie(movies = state.movies, favoriteMovieViewModel = favoriteMovieViewModel, navController = navController)

                }
            }

        else -> Box {

        }
    }
}

@Composable
fun ListFavoriteMovie(
    movies: List<MovieEntity>,
    favoriteMovieViewModel: FavoriteMovieViewModel,
    navController: NavHostController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(movies) {
            FavoriteMovieCard(movie = it, favoriteMovieViewModel = favoriteMovieViewModel, navController = navController)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FavoriteMovieCard(movie: MovieEntity, favoriteMovieViewModel: FavoriteMovieViewModel, navController: NavHostController) {
    Box(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12))
                .clickable {
                   navController.navigate(MovieScreens.MOVIE_DETAIL.name+"/${movie.id}")
                },
            shape = RoundedCornerShape(12)
        ) {
            GlideImage(
                model = "${ApiConstants.BASE_IMAGE_URL}${movie.posterPath}",
                contentDescription = "poster image",
                contentScale = ContentScale.FillBounds
            )
        }

        IconButton(
            modifier = Modifier.align(alignment = Alignment.TopEnd),
            onClick = {
            favoriteMovieViewModel.deleteFavoriteMovie(movie)
        }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "icon delete",
                tint = Color.White
            )
        }
    }
}
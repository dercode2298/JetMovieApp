package com.example.movieappcleanarichitecture.presentation.movie_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.presentation.favorite_movie.FavoriteMovieViewModel

@Composable
fun MovieDetailAppBar(modifier: Modifier = Modifier, movie: MovieEntity, favoriteMovieViewModel: FavoriteMovieViewModel = hiltViewModel(), onBackClicked:() -> Unit) {
    LaunchedEffect(true) {
        favoriteMovieViewModel.isFavoriteMovie(movieId = movie.id)
    }
    val state = favoriteMovieViewModel.state.value
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier
                .size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        if(state is FavoriteMovieViewModel.State.CheckFavoriteMovie){
            IconButton(
                onClick = {
                          favoriteMovieViewModel.toggleFavoriteButton(movie = movie, isFavorite = state.isFavoriteMovie)
                },
                modifier = Modifier
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = if(state.isFavoriteMovie) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "favorite button",
                    tint = Color.White
                )
            }
        }
        if(state is FavoriteMovieViewModel.State.Error){
            throw state.error
        }

//        when (favoriteMovieState) {
//            is IsFavoriteMovie -> {
//                IconButton(
//                    onClick = { onFavoriteClicked() },
//                    modifier = Modifier
//                        .size(32.dp)
//                        .background(MaterialTheme.colorScheme.primary)
//                ) {
//                    Icon(
//                        imageVector = if (favoriteMovieState.isFavoriteMovie) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
//                        contentDescription = "Favorite",
//                        tint = Color.White
//                    )
//                }
//            }
//            else -> {
//                // Default state when loading or an error occurs
//                Icon(
//                    imageVector = Icons.Default.FavoriteBorder,
//                    contentDescription = "Favorite",
//                    tint = Color.White,
//                    modifier = Modifier
//                        .size(32.dp)
//                        .background(MaterialTheme.colorScheme.primary)
//                )
//            }
//        }
    }
}

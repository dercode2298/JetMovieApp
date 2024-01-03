package com.example.movieappcleanarichitecture.presentation.movie_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.movieappcleanarichitecture.common.extentions.convertToPercentageString
import com.example.movieappcleanarichitecture.common.extentions.intelliTrim
import com.example.movieappcleanarichitecture.components.ErrorWidget
import com.example.movieappcleanarichitecture.components.GradientButton
import com.example.movieappcleanarichitecture.components.LoadingWidget
import com.example.movieappcleanarichitecture.data.core.ApiConstants
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.navigation.MovieScreens
import com.example.movieappcleanarichitecture.ui.theme.AppColor


@Composable
fun MovieDetailScreen(
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel(),
    navController: NavHostController,
    movieId: Int
) {
    LaunchedEffect(true) {
        movieDetailViewModel.getMovieDetail(movieId)
    }
    val state = movieDetailViewModel.state.value
    Scaffold {
        when (state) {
            is MovieDetailViewModel.State.Loading ->
                LoadingWidget()

            is MovieDetailViewModel.State.Error ->
                ErrorWidget()

            is MovieDetailViewModel.State.Loaded ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .background(MaterialTheme.colorScheme.primary),
                ) {
                    BigPoster(movie = state.movie, it, navHostController = navController)
                    Text(
                        modifier = Modifier.padding(vertical = 22.dp, horizontal = 18.dp),
                        text = state.movie.overview!!,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            letterSpacing = 0.25.sp
                        )
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = 18.dp),
                        text = "Cast",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                    )

                    CastWidget(movieId)

                    GradientButton(
                        text = "Watch trailers", gradient = Brush.linearGradient(
                            listOf(
                                AppColor.royalBlue,
                                AppColor.violet
                            ),
                        ),
                        modifier = Modifier.clip(RoundedCornerShape(10)).fillMaxWidth(),
                        textStyle = MaterialTheme.typography.titleMedium.copy(Color.White)
                    ){
                        navController.navigate("${MovieScreens.WATCH_TRAILER.name}/$movieId")
                    }
                }

            else -> Box {}
        }

    }
}

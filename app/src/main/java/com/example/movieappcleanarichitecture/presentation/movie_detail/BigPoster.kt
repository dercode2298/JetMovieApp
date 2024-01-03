package com.example.movieappcleanarichitecture.presentation.movie_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.movieappcleanarichitecture.common.extentions.convertToPercentageString
import com.example.movieappcleanarichitecture.data.core.ApiConstants
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.ui.theme.AppColor

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BigPoster(movie: MovieEntity, safePaddingValue: PaddingValues, navHostController: NavHostController) {
    Box(

    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter),
        ) {
            GlideImage(
                model = "${ApiConstants.BASE_IMAGE_URL}${movie.posterPath}",
                contentDescription = null, // Add description if needed
                contentScale = ContentScale.FillWidth,
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                MaterialTheme.colorScheme.primary.copy(alpha = 1f)
                            ),
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .align(alignment = Alignment.BottomCenter),
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.weight(1f),
                    softWrap = true
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = movie.voteAverage.convertToPercentageString(),
                    style = MaterialTheme.typography.titleLarge.copy(AppColor.violet),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Text(
                text = movie.releaseDate,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Color.Gray.copy(0.6f),
                    fontSize = 16.sp
                )
            )
        }

        MovieDetailAppBar(
            modifier = Modifier.padding(safePaddingValue),
            movie = movie
        ) {
            navHostController.popBackStack()
        }
    }
}
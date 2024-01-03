package com.example.movieappcleanarichitecture.presentation.movie_carousel

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.movieappcleanarichitecture.data.core.ApiConstants
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.ui.theme.AppColor

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieBackdropWidget(movie: MovieEntity) {
    Card(
        modifier = Modifier
            .fillMaxHeight(0.75f)
            .fillMaxWidth()
            .background(color = Color.Transparent),
        shape = RoundedCornerShape(
            bottomEndPercent = 8,
            bottomStartPercent = 8
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp)
    ) {
        GlideImage(
            model = "${ApiConstants.BASE_IMAGE_URL}${movie.backDropPath}", contentDescription = "",
            alignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
            loading = placeholder(ColorPainter(AppColor.vulcan)),
            contentScale = ContentScale.FillHeight,
            transition = CrossFade(
                animationSpec = tween(300)
            )
        )
    }
}

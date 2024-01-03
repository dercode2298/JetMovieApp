package com.example.movieappcleanarichitecture.presentation.movie_carousel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movieappcleanarichitecture.data.core.ApiConstants
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.navigation.MovieScreens
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviePageView(
    modifier: Modifier = Modifier,
    movies: List<MovieEntity>,
    pagerState: PagerState,
    navController: NavHostController,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxHeight(0.75f),
        contentPadding = PaddingValues(horizontal = 86.dp),
        pageSpacing = 36.dp,

        ) { page ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            )
                    lerp(
                        start = 0.8f,
                        stop = 1f,
                        fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                }.clip(RoundedCornerShape(22.dp))
                .clickable {
                    navController.navigate(MovieScreens.MOVIE_DETAIL.name+"/${movies[pagerState.currentPage].id}")
                },
            shape = RoundedCornerShape(22.dp),
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = "${ApiConstants.BASE_IMAGE_URL}${movies[page].posterPath}",
                contentDescription = "image",
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

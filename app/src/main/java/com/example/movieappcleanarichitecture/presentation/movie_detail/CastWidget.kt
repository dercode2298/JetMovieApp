package com.example.movieappcleanarichitecture.presentation.movie_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.movieappcleanarichitecture.components.ErrorWidget
import com.example.movieappcleanarichitecture.components.LoadingWidget
import com.example.movieappcleanarichitecture.data.core.ApiConstants
import com.example.movieappcleanarichitecture.ui.theme.AppColor


@Composable
fun CastWidget(movieId: Int, castViewModel: CastViewModel = hiltViewModel()) {
    LaunchedEffect(true) {
        castViewModel.loadCasts(movieId = movieId)
    }
    val state = castViewModel.state.value

    when(state){
        is CastViewModel.State.Loading -> LoadingWidget()
        is CastViewModel.State.Error -> ErrorWidget()
        is CastViewModel.State.Loaded ->
            LazyRow(
                Modifier
                    .height(280.dp)
                    .padding(vertical = 18.dp).padding(start = 18.dp),
            ) {
                items(state.casts){ cast ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.clip(RoundedCornerShape(16.dp)).height(230.dp).width(140.dp)
                    ) {
                        Column(
                            modifier = Modifier.background(Color.White).fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {

                            AsyncImage(
                                modifier = Modifier.weight(1f).fillMaxWidth().padding(bottom = 6.dp),
                                model = "${ApiConstants.BASE_IMAGE_URL}${cast.profilePath}",
                                contentDescription = "image",
                                contentScale = ContentScale.FillWidth
                            )

                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                text = cast.name,
                                maxLines = 1,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = AppColor.vulcan,
                                    fontWeight = FontWeight(600)
                                )
                            )

                            Text(
                                modifier = Modifier.padding(bottom = 6.dp, start = 10.dp, end = 10.dp),
                                text = cast.character,
                                maxLines = 1,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.bodyLarge.copy(color = AppColor.royalBlue)
                            )

                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                }
            }
        else -> Box{}
    }
}




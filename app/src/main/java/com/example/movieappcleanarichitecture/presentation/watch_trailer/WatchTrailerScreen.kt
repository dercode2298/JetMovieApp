package com.example.movieappcleanarichitecture.presentation.watch_trailer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieappcleanarichitecture.components.ErrorWidget
import com.example.movieappcleanarichitecture.components.LoadingWidget
import com.example.movieappcleanarichitecture.ui.theme.AppColor
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun WatchTrailerScreen(
    movieId: Int,
    navController: NavHostController,
    watchTrailerViewModel: WatchTrailerViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        watchTrailerViewModel.getVideos(movieId)
    }
    val UIState = watchTrailerViewModel.state.value
    val selectedIndex = remember {
        mutableIntStateOf(0)
    }

    when (UIState) {
        is WatchTrailerViewModel.State.Loading -> LoadingWidget()
        is WatchTrailerViewModel.State.Error -> ErrorWidget()
        is WatchTrailerViewModel.State.Loaded ->
            Scaffold {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppColor.vulcan)
                ) {
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
                                contentDescription = "Back icon"
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Text(
                            text = "Watch Trailers",
                            style = MaterialTheme.typography.titleMedium.copy(Color.White)
                        )
                    }

                    AndroidView(
                        factory = {
                            var view = YouTubePlayerView(it)
                            view.addYouTubePlayerListener(
                                object : AbstractYouTubePlayerListener() {
                                    override fun onReady(youTubePlayer: YouTubePlayer) {
                                        super.onReady(youTubePlayer)
                                        youTubePlayer.loadVideo(UIState.casts[selectedIndex.intValue].key, 0f)
                                    }

                                    override fun onStateChange(
                                        youTubePlayer: YouTubePlayer,
                                        state: PlayerConstants.PlayerState
                                    ) {
                                        super.onStateChange(youTubePlayer, state)
                                        if (state == PlayerConstants.PlayerState.ENDED) {
                                            if (UIState.casts.size > selectedIndex.intValue){
                                                selectedIndex.intValue++
                                            } else{
                                                selectedIndex.intValue = 0
                                            }
                                            youTubePlayer.loadVideo(UIState.casts[selectedIndex.intValue].key, 0f)
                                        }
                                    }
                                }
                            )
                            view
                        })
                }
            }

        else -> Box{}
    }

}
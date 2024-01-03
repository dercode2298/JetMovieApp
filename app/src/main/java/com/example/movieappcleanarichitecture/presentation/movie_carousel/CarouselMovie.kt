import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.presentation.movie_carousel.MovieCarouselViewModel
import com.example.movieappcleanarichitecture.presentation.movie_carousel.MoviePageView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselMovie(movieCarouselViewModel: MovieCarouselViewModel, movies: List<MovieEntity>?, navController: NavHostController, getCurrentMovie: (MovieEntity) -> Unit){
    if (movieCarouselViewModel.requestState.value.loading == true) {
        Log.d("TAG", "loading....")
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }
    } else {
        Log.d("TAG", "ok....")


        if (movies != null) {
            val pagerState = rememberPagerState(initialPage = 0) {
                movies.size
            }
            getCurrentMovie(movies[pagerState.currentPage])
            Log.d("TAG", "MovieCarouselWidget: $movies")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MoviePageView(navController = navController,movies = movies.toMutableList(), pagerState = pagerState)
                Spacer(modifier = Modifier.height(10.dp))
                MovieTitleWidget(title = movies[pagerState.currentPage].title)
            }
        }
    }
}
package com.example.movieappcleanarichitecture.presentation.movie_tabbed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movieappcleanarichitecture.common.extentions.intelliTrim
import com.example.movieappcleanarichitecture.components.LoadingWidget
import com.example.movieappcleanarichitecture.data.core.ApiConstants
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.navigation.MovieScreens
import com.example.movieappcleanarichitecture.ui.theme.AppColor

sealed class TabMenu(
    val title: String,
) {
    data object PopularTab : TabMenu(title = "Popular", )
    data object NowTab : TabMenu(title = "Now",)
    data object SoonTab : TabMenu(title = "Coming Soon",)
}

private val TabMenus = listOf(
    TabMenu.PopularTab,
    TabMenu.NowTab,
    TabMenu.SoonTab
)

@Composable
fun MovieTabbedWidget(movieTabbedViewModel: MovieTabbedViewModel = hiltViewModel(), navController: NavHostController) {

    val tabbedState = movieTabbedViewModel.state

    val selectedIndex = remember {
        mutableIntStateOf(0)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .background(AppColor.vulcan),
        verticalArrangement = Arrangement.Top,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TabMenus.forEachIndexed { index, tab ->
                val selected = selectedIndex.intValue == index
                TabItem(selected = selected, title = tab.title) {
                    selectedIndex.intValue = index
                    movieTabbedViewModel.getTabData(index)
                }
            }
        }

        if (tabbedState.value.loading == true){
            LoadingWidget()
        } else{
            MovieTabbedListView(tabbedState.value.data!!, selectedIndex.intValue, navController = navController)
        }

    }
}

@Composable
fun MovieTabbedListView(movies: List<MovieEntity>, selectedIndex:Int = 0, navController: NavHostController){
    LazyRow(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(10.dp),

    ) {
        items(movies){movie ->
            MovieTabCardWidget(movieId = movie.id, title = movie.title, posterPath = movie.posterPath, navController = navController)
        }
    }
}

@Composable
fun MovieTabCardWidget(movieId:Int, title: String, posterPath:String, navController: NavHostController){
    Column(
        modifier = Modifier.padding(end = 18.dp)
            .clickable {
                navController.navigate(MovieScreens.MOVIE_DETAIL.name+"/$movieId")
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                    .weight(1f),
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = "${ApiConstants.BASE_IMAGE_URL}$posterPath",
                contentDescription = "image",
                contentScale = ContentScale.FillWidth
            )
        }
        Text(
            modifier = Modifier.padding(bottom = 10.dp, top = 4.dp),
            text = title.intelliTrim(),
            maxLines = 1,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
        )

    }
}

@Composable
fun TabItem(selected: Boolean, title: String, onClick: () -> Unit) {
    Column{
        Text(
            modifier = Modifier
                .clickable { onClick.invoke() }
                .drawBehind {
                    val strokeWidthPx = 1.dp.toPx()
                    val verticalOffset = size.height - 2.sp.toPx()
                    drawLine(
                        color = if (selected) AppColor.royalBlue else Color.Transparent,
                        strokeWidth = strokeWidthPx,
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset)
                    )
                },
            text = title,
            style = if (selected) MaterialTheme.typography.labelMedium.copy(
                color = AppColor.royalBlue
            ) else MaterialTheme.typography.labelMedium,
        )
    }
}
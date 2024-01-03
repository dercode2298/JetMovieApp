package com.example.movieappcleanarichitecture.presentation.movie_carousel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.movieappcleanarichitecture.R
import com.example.movieappcleanarichitecture.navigation.MovieScreens
import kotlinx.coroutines.launch


@Composable
fun MovieAppBar(drawerState: DrawerState, navController: NavHostController) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .height(50.dp)
    ) {

        //icon menu
        IconButton(
            onClick = {
                scope.launch {
                    drawerState.apply {
                        if(isClosed) open() else close()
                    }
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menu_ic),
                contentDescription = "menu icon",
                tint = Color.White
            )
        }

        //logo
        Image(
            modifier = Modifier.padding(vertical = 6.dp).weight(1f),
            painter = painterResource(id = R.drawable.logo), contentDescription = "logo")

        //icon search
        IconButton(
            onClick = {
                navController.navigate(MovieScreens.SEARCH_MOVIE.name)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search icon",
                tint = Color.White
            )
        }

    }
}

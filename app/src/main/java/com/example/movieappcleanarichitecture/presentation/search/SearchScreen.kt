package com.example.movieappcleanarichitecture.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.movieappcleanarichitecture.components.ErrorWidget
import com.example.movieappcleanarichitecture.components.LoadingWidget
import com.example.movieappcleanarichitecture.data.core.ApiConstants
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.navigation.MovieScreens
import com.example.movieappcleanarichitecture.presentation.favorite_movie.ListFavoriteMovie
import com.example.movieappcleanarichitecture.ui.theme.AppColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    searchViewModel: SeachViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state = searchViewModel.state.value
    val active = remember {
        mutableStateOf(false)
    }

    val searchTerm = remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(true) {
        focusRequester.requestFocus()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Column {
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
                        contentDescription = "Back icon",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "Search Movie",
                    style = MaterialTheme.typography.titleMedium.copy(Color.White)
                )
            }

//            SearchBar(
//                modifier = Modifier.padding(horizontal = 16.dp),
//                query = searchTerm.value,
//                onQueryChange = {queryString ->
//                    searchTerm.value = queryString
//                    if(searchTerm.value.length >= 3){
//                        searchViewModel.getSearchMovies(searchTerm.value)
//                    } else{
//                        searchViewModel.clearSearchMovie()
//                    }
//                },
//                onSearch = {queryString ->
//                    if(searchTerm.value.length >= 3){
//                        searchViewModel.getSearchMovies(searchTerm.value)
//                    }
//                },
//                active = false,
//                onActiveChange = {active.value = it},
//                colors = SearchBarDefaults.colors(
//                    dividerColor = Color.White,
//                    inputFieldColors = TextFieldDefaults.colors(focusedTextColor = Color.White, focusedIndicatorColor = Color.White, ))
//            ) {
//
//            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(focusRequester = focusRequester),
                value = searchTerm.value,
                onValueChange = { queryString ->
                    searchTerm.value = queryString
                    if (searchTerm.value.length >= 3) {
                        searchViewModel.getSearchMovies(searchTerm.value)
                    } else {
                        searchViewModel.clearSearchMovie()
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    },
                    onSearch = {
                        keyboardController?.hide()
                    }
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedSupportingTextColor = Color.White,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(16)
            )

            when (state) {
                is SeachViewModel.State.Loading -> LoadingWidget(
                    progressAlignment = Alignment.TopCenter,
                    modifier = Modifier.padding(top = 16.dp)
                )

                is SeachViewModel.State.Error -> ErrorWidget()
                is SeachViewModel.State.Loaded ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                            .background(AppColor.vulcan)
                    ) {
                        items(state.movies) {
                            SearchItem(movie = it, navHostController = navController)
                        }
                    }

                else -> Box {

                }
            }

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SearchItem(movie: MovieEntity, navHostController: NavHostController) {
    Row(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .clickable { navHostController.navigate(MovieScreens.MOVIE_DETAIL.name+"/${movie.id}") },
    ) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(16))
                .fillMaxWidth(0.4f)
                .padding(10.dp),
            shape = RoundedCornerShape(16)
        ) {
            GlideImage(
                model = "${ApiConstants.BASE_IMAGE_URL}${movie.posterPath}",
                contentDescription = "image movie",
                contentScale = ContentScale.FillWidth
            )
        }

        Column(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Text(text = movie.title, style = MaterialTheme.typography.titleMedium.copy(Color.White))
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = movie.overview.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    Color.White,
                    letterSpacing = 0.25.sp
                ),
                softWrap = true,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
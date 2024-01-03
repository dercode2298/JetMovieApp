package com.example.movieappcleanarichitecture.presentation.movie_carousel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcleanarichitecture.data.core.DataOrException
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.domain.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieCarouselViewModel @Inject constructor(private  val repository: MovieRepository) : ViewModel() {
    val requestState : MutableState<DataOrException<List<MovieEntity>>> = mutableStateOf(
        DataOrException(null, null, Exception(""))
    )

    init {
        getTrending()
    }

    private fun getTrending(){
        viewModelScope.launch {
            requestState.value = requestState.value.copy(
                loading = true
            )
            val response = repository.getTrending()
            response.fold(
                fnL = { exception ->
                    Log.d("TAG", "getTrending: ${exception.message}")
                    requestState.value = requestState.value.copy(
                        loading = false,
                        data = null,
                        error = exception
                    )
                },
                fnR = { movies ->
                    Log.d("TAG", "getTrending: ${movies?.size}")
                    requestState.value = requestState.value.copy(
                        loading = false,
                        data = movies,
                        error = null
                    )
                }
            )
            requestState.value.loading = false
        }
    }
}
package com.example.movieappcleanarichitecture.presentation.movie_detail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.domain.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository : MovieRepository) : ViewModel() {
    sealed class State{
        data object Loading : State()
        data class Loaded(val movie: MovieEntity) : State()
        data class Error(val error: Exception) : State()
    }

    val state : MutableState<State?> = mutableStateOf(null)

    fun getMovieDetail(movieId:Int){
        viewModelScope.launch {
            state.value = State.Loading
            val response = repository.getMovieDetail(id = movieId)
            response.fold(
                {exception ->
                    state.value = State.Error(exception)
                },
                { movie ->
                    state.value = State.Loaded(movie = movie!!)
                }
            )
            Log.d("TAG", "getMovieDetail: ${state.value.toString()}")
        }
    }

}
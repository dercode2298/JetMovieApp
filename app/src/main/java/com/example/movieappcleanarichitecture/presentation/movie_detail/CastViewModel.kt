package com.example.movieappcleanarichitecture.presentation.movie_detail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcleanarichitecture.domain.entities.CastEntity
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.domain.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(val repository: MovieRepository) : ViewModel(){
    sealed class State{
        data object Loading : State()
        data class Loaded(val casts: List<CastEntity>) : State()
        data class Error(val error: Exception) : State()
    }

    val state : MutableState<State?> = mutableStateOf(null)

    fun loadCasts(movieId:Int){
        viewModelScope.launch {
            state.value = State.Loading
            val response = repository.getCastCrew(id = movieId)
            response.fold(
                {exception ->
                    state.value = State.Error(exception)
                },
                { casts ->
                    state.value = State.Loaded(casts = casts!!)
                }
            )
            Log.d("TAG", "getMovieDetail: ${state.value.toString()}")
        }
    }

}
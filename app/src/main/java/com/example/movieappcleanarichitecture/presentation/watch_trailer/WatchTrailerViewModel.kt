package com.example.movieappcleanarichitecture.presentation.watch_trailer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcleanarichitecture.domain.entities.CastEntity
import com.example.movieappcleanarichitecture.domain.entities.VideoEntity
import com.example.movieappcleanarichitecture.domain.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchTrailerViewModel @Inject constructor(val repository: MovieRepository) : ViewModel() {
    sealed class State{
        data object Loading : State()
        data class Loaded(val casts: ArrayList<VideoEntity>) : State()
        data class Error(val error: Exception) : State()
    }

    val state : MutableState<State?> = mutableStateOf(null)

    fun getVideos(movieId : Int){
        viewModelScope.launch {
            state.value = State.Loading
            val response = repository.getVideos(movieId)
            response.fold(
                {
                    state.value = State.Error(it)
                },
                {
                    state.value = State.Loaded(it!!)
                }
            )
        }
    }

}
package com.example.movieappcleanarichitecture.presentation.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.domain.entities.VideoEntity
import com.example.movieappcleanarichitecture.domain.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeachViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    sealed class State{
        data object Loading : State()
        data class Loaded(val movies: List<MovieEntity>) : State()
        data class Error(val error: Exception) : State()
    }

    val state : MutableState<State?> = mutableStateOf(null)

    fun getSearchMovies(searchTerm : String){
        viewModelScope.launch {
            state.value = State.Loading
            val response = repository.getSearchMovies(searchTerm)
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

    fun clearSearchMovie(){
        state.value = State.Loaded(emptyList())
    }
}
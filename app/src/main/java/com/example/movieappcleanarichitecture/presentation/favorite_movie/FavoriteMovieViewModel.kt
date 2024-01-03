package com.example.movieappcleanarichitecture.presentation.favorite_movie

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
class FavoriteMovieViewModel @Inject constructor(private val repository: MovieRepository) :ViewModel() {
    sealed class State{
        data object Loading : State()
        data class Loaded(val movies: List<MovieEntity>) : State()
        data class Error(val error: Exception) : State()
        data class CheckFavoriteMovie(val isFavoriteMovie:Boolean):State()
    }

    val state : MutableState<State?> = mutableStateOf(null)

    fun getFavoriteMovies(){
        viewModelScope.launch {
            state.value = State.Loading
            try {
               repository.getFavoriteMovies().collect{
                    state.value = State.Loaded(movies = it)
               }
            } catch (e:Exception){
                state.value = State.Error(e)
            }
        }
    }

    fun isFavoriteMovie(movieId:Int){
        viewModelScope.launch {
            try {
                repository.checkIfFavoriteMovie(movieId).collect{
                    state.value = State.CheckFavoriteMovie(isFavoriteMovie = it!=0)
                }
            } catch (e:Exception){
                state.value = State.Error(e)
            }
        }
    }

    fun deleteFavoriteMovie(movie: MovieEntity){
        viewModelScope.launch {
            try {
                repository.deleteFavoriteMovie(movie = movie)
            } catch (e:Exception){
                state.value = State.Error(e)
            }
        }
    }

    fun toggleFavoriteButton(movie : MovieEntity, isFavorite:Boolean) = viewModelScope.launch {
        viewModelScope.launch {
            if(isFavorite){
                repository.deleteFavoriteMovie(movie)
            } else{
                repository.addFavoriteMovie(movie)
            }
            isFavoriteMovie(movie.id)
        }
    }
}
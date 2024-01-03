package com.example.movieappcleanarichitecture.presentation.movie_tabbed

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcleanarichitecture.data.core.DataOrException
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.domain.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieTabbedViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    val state : MutableState<DataOrException<List<MovieEntity>>> = mutableStateOf(
        DataOrException(emptyList(),null, Exception(""))
    )

    init {
        getPopular()
    }

    fun getTabData(selectedIndex : Int){
        when(selectedIndex){
            0 -> getPopular()
            1 -> getNowPlaying()
            2 -> getComingSoon()
            else -> getPopular()
        }
    }

    private fun getPopular(){
        viewModelScope.launch {
            state.value = state.value.copy(loading = true)
            val response = repository.getPopular()
            response.fold(
                {
                    state.value = state.value.copy(
                        error = it
                    )
                },
                {
                    state.value = state.value.copy(
                        data = it
                    )
                }
            )

            state.value = state.value.copy(loading = false)
        }
    }

    private fun getNowPlaying(){
        viewModelScope.launch {
            state.value = state.value.copy(loading = true)
            val response = repository.getPlayingNow()
            response.fold(
                {
                    state.value = state.value.copy(
                        error = it
                    )
                },
                {
                    state.value = state.value.copy(
                        data = it
                    )
                }
            )

            state.value = state.value.copy(loading = false)
        }
    }

    private fun getComingSoon(){
        viewModelScope.launch {
            state.value = state.value.copy(loading = true)
            val response = repository.getComingSoon()
            response.fold(
                {
                    state.value = state.value.copy(
                        error = it
                    )
                },
                {
                    state.value = state.value.copy(
                        data = it
                    )
                }
            )

            state.value = state.value.copy(loading = false)
        }
    }

}
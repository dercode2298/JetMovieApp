package com.example.movieappcleanarichitecture.data.model

import com.example.movieappcleanarichitecture.domain.entities.MovieEntity

data class MovieResponseModel(val page: Int, var results: List<MovieEntity>)
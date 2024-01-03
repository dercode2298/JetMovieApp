package com.example.movieappcleanarichitecture.data.model

import com.example.movieappcleanarichitecture.domain.entities.CastEntity

data class CastResponseModel(val page: Int, var cast: List<CastEntity>)
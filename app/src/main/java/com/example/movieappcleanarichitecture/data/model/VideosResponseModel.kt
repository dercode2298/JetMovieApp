package com.example.movieappcleanarichitecture.data.model

import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.domain.entities.VideoEntity

data class VideosResponseModel(val page: Int, var results: ArrayList<VideoEntity>)
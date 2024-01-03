package com.example.movieappcleanarichitecture.domain.entities

import com.google.gson.annotations.SerializedName

data class CastEntity(
    @SerializedName("credit_id")
    val creditId: String,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String,
    val character: String
)
package com.example.movieappcleanarichitecture.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies_tbl")
data class MovieEntity(
    @ColumnInfo("poster_path")
    @SerializedName("poster_path")
    val posterPath: String,

    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @ColumnInfo("backdrop_path")
    @SerializedName("backdrop_path")
    val backDropPath: String,

    @ColumnInfo("title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo("vote_average")
    @SerializedName("vote_average")
    val voteAverage: Double,

    @ColumnInfo("release_date")
    @SerializedName("release_date")
    val releaseDate: String,

    @ColumnInfo("overview")
    @SerializedName("overview")
    val overview: String? = null,
)
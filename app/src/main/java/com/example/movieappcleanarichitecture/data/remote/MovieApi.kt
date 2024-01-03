package com.example.movieappcleanarichitecture.data.remote

import com.example.movieappcleanarichitecture.data.model.CastResponseModel
import com.example.movieappcleanarichitecture.data.model.MovieResponseModel
import com.example.movieappcleanarichitecture.data.model.VideosResponseModel
import com.example.movieappcleanarichitecture.domain.entities.CastEntity
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.domain.entities.VideoEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface MovieApi {

    @GET("trending/movie/day")
    suspend fun getTrending() : MovieResponseModel?

    @GET("search/movie")
    suspend fun getSearchMovies(@Query("query") searchTerm:String) : MovieResponseModel?

    @GET("movie/popular")
    suspend fun getPopular() : MovieResponseModel?

    @GET("movie/now_playing")
    suspend fun getPlayingNow() : MovieResponseModel?

    @GET("movie/upcoming")
    suspend fun getComingSoon() : MovieResponseModel?

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Int) : MovieEntity?

    @GET("movie/{id}/credits")
    suspend fun getCastCrew(@Path("id") id: Int) : CastResponseModel?

    @GET("movie/{id}/videos")
    suspend fun getVideos(@Path("id") id: Int) : VideosResponseModel?

}
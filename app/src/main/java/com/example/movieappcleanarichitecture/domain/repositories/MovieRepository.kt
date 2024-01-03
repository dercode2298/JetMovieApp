package com.example.movieappcleanarichitecture.domain.repositories

import android.util.Log
import com.example.movieappcleanarichitecture.data.core.Either
import com.example.movieappcleanarichitecture.data.local.MovieDao
import com.example.movieappcleanarichitecture.data.remote.MovieApi
import com.example.movieappcleanarichitecture.domain.entities.CastEntity
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import com.example.movieappcleanarichitecture.domain.entities.VideoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: MovieApi, private val movieDao: MovieDao) {

    suspend fun getTrending() : Either<Exception,  List<MovieEntity>?>{
        try {
//            Log.d("TAG", "getTrending: ${api.getTrending().toString()}")
            return Either.Right(api.getTrending()?.results)
        } catch (e:Exception){
            e.printStackTrace()
            return Either.Left(e)
        }
    }

    suspend fun getSearchMovies(searchTerm:String) : Either<Exception,  List<MovieEntity>?>{
        try {
            return Either.Right(api.getSearchMovies(searchTerm = searchTerm)?.results)
        } catch (e:Exception){
            e.printStackTrace()
            return Either.Left(e)
        }
    }

    suspend fun getPopular() : Either<Exception,  List<MovieEntity>?>{
        try {
            return Either.Right(api.getPopular()?.results)
        } catch (e:Exception){
            e.printStackTrace()
            return Either.Left(e)
        }
    }

    suspend fun getPlayingNow() : Either<Exception,  List<MovieEntity>?>{
        try {
            return Either.Right(api.getPlayingNow()?.results)
        } catch (e:Exception){
            e.printStackTrace()
            return Either.Left(e)
        }
    }

    suspend fun getComingSoon() : Either<Exception,  List<MovieEntity>?>{
        try {
            return Either.Right(api.getComingSoon()?.results)
        } catch (e:Exception){
            e.printStackTrace()
            return Either.Left(e)
        }
    }

    suspend fun getMovieDetail(id:Int) : Either<Exception,  MovieEntity?>{
        try {
            return Either.Right(api.getMovieDetail(id = id))
        } catch (e:Exception){
            e.printStackTrace()
            return Either.Left(e)
        }
    }

    suspend fun getCastCrew(id:Int) : Either<Exception, List<CastEntity>?>{
        try {
            Log.d("TAG", "getCastCrew: ${api.getCastCrew(id = id)?.cast}")
            return Either.Right(api.getCastCrew(id = id)?.cast)
        } catch (e:Exception){
            e.printStackTrace()
            return Either.Left(e)
        }
    }

    suspend fun getVideos(id:Int) : Either<Exception, ArrayList<VideoEntity>?>{
        try {
            return Either.Right(api.getVideos(id = id)?.results)
        } catch (e:Exception){
            e.printStackTrace()
            return Either.Left(e)
        }
    }


    //local
    fun getFavoriteMovies() : Flow<List<MovieEntity>> = movieDao.getFavoriteMovies().flowOn(Dispatchers.IO).conflate()
    suspend fun addFavoriteMovie(movie:MovieEntity) = movieDao.addFavoriteMovie(movie = movie)
    suspend fun deleteFavoriteMovie(movie:MovieEntity) = movieDao.deleteFavoriteMovie(movie = movie)
    fun checkIfFavoriteMovie(movieId:Int) = movieDao.checkIsFavoriteMovie(movieId).flowOn(Dispatchers.IO).conflate()
}
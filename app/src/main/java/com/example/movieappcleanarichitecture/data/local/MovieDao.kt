package com.example.movieappcleanarichitecture.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieappcleanarichitecture.domain.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("select * from movies_tbl")
    fun getFavoriteMovies() : Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteFavoriteMovie(movie: MovieEntity)

    @Query("select count() from movies_tbl where id = :id")
    fun checkIsFavoriteMovie(id:Int) : Flow<Int>
}
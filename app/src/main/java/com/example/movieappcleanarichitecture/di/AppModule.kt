package com.example.movieappcleanarichitecture.di

import android.content.Context
import androidx.room.Room
import com.example.movieappcleanarichitecture.data.core.ApiConstants
import com.example.movieappcleanarichitecture.data.local.MovieDao
import com.example.movieappcleanarichitecture.data.local.MovieDatabase
import com.example.movieappcleanarichitecture.data.remote.MovieApi
import com.example.movieappcleanarichitecture.domain.repositories.MovieRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieApi() : MovieApi{
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
        client.addInterceptor(
            Interceptor{
                val request = it.request().newBuilder().addHeader(
                    "Content-Type", "application/json"
                ).addHeader(  "Authorization", ApiConstants.ACCESS_TOKEN).build()
                return@Interceptor it.proceed(request)
            }
        )
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(api : MovieApi, dao: MovieDao) : MovieRepository{
        return MovieRepository(api, dao)
    }


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context, ) : MovieDatabase = Room.databaseBuilder(
        context = context,
        MovieDatabase::class.java,
        "movies_db"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase) : MovieDao = movieDatabase.movieDao()





}
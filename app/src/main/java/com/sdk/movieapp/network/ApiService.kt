package com.sdk.movieapp.network

import com.sdk.movieapp.model.MovieSearchResponse
import com.sdk.movieapp.model.Movies
import com.sdk.movieapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("3/movie/popular")
    suspend fun getMovies(
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("page") page: Int = 1
    ): Movies

    @GET("3/search/movie")
    suspend fun searchMovie(
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): MovieSearchResponse

    @GET("3/movie/{movie_id}")
    suspend fun searchById(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = Constants.API_KEY
    )
}
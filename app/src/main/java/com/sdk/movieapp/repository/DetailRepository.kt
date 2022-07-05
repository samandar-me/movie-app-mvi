package com.sdk.movieapp.repository

import com.sdk.movieapp.database.MovieDatabase
import com.sdk.movieapp.model.Result

class DetailRepository(
    private val db: MovieDatabase
){
    suspend fun saveMovie(result: Result) = db.movieDao().saveMovie(result)
    suspend fun deleteMovie(result: Result) = db.movieDao().deleteMovie(result)
}
package com.sdk.movieapp.repository

import com.sdk.movieapp.database.MovieDatabase

class FavoriteRepository(
    private val db: MovieDatabase
) {
    fun getAllMovies() = db.movieDao().getAllMovies()
    suspend fun deleteAllMovies() = db.movieDao().deleteAllMovies()
}
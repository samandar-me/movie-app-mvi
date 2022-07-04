package com.sdk.movieapp.repository

import com.sdk.movieapp.network.ApiService

class MovieRepository(private val apiService: ApiService) {
    suspend fun getMovies(page: Int = 1) = apiService.getMovies(page = page)
}
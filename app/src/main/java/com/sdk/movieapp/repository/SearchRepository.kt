package com.sdk.movieapp.repository

import com.sdk.movieapp.network.ApiService

class SearchRepository(private val apiService: ApiService) {
    suspend fun searchMovie(query: String) = apiService.searchMovie(query = query)
}
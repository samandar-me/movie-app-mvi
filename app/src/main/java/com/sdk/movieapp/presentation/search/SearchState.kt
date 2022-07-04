package com.sdk.movieapp.presentation.search

import com.sdk.movieapp.model.MovieSearchResponse

sealed class SearchState {
    object Init: SearchState()
    object Loading: SearchState()
    data class MoviesData(val movies: MovieSearchResponse): SearchState()
    data class Error(val error: String?): SearchState()
}

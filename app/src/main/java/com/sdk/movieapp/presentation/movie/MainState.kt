package com.sdk.movieapp.presentation.movie

import com.sdk.movieapp.model.Movies

sealed class MainState {
    object Init: MainState()
    object Loading: MainState()
    data class MoviesData(val movies: Movies): MainState()
    data class Error(val error: String?): MainState()
}
package com.sdk.movieapp.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.movieapp.network.RetroInstance
import com.sdk.movieapp.repository.MovieRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val movieRepository = MovieRepository(RetroInstance.apiService)

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState.Init)
    val state: StateFlow<MainState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchMovies -> fetchMovies()
                }
            }
        }
    }

    private fun fetchMovies(page: Int = 1) {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.MoviesData(movieRepository.getMovies(page))
            } catch (e: Exception) {
                MainState.Error(e.stackTraceToString())
            }
        }
    }
}
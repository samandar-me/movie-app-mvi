package com.sdk.movieapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.movieapp.network.RetroInstance
import com.sdk.movieapp.repository.SearchRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val searchRepository = SearchRepository(RetroInstance.apiService)

    private val _state: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Init)
    val state: StateFlow<SearchState> get() = _state

    fun searchUser(query: String) {
        viewModelScope.launch {
            _state.value = SearchState.Loading
            _state.value = try {
                SearchState.MoviesData(searchRepository.searchMovie(query))
            } catch (e: Exception) {
                SearchState.Error(e.stackTraceToString())
            }
        }
    }
}
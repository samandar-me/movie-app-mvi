package com.sdk.movieapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.movieapp.model.Result
import com.sdk.movieapp.repository.DetailRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val detailRepository: DetailRepository
): ViewModel() {
    fun saveMovie(result: Result) = viewModelScope.launch {
        detailRepository.saveMovie(result)
    }
    fun deleteMovie(result: Result) = viewModelScope.launch {
        detailRepository.deleteMovie(result)
    }
}
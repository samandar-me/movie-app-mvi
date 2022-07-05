package com.sdk.movieapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sdk.movieapp.repository.DetailRepository

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val detailRepository: DetailRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(detailRepository) as T
    }
}
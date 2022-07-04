package com.sdk.movieapp.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.movieapp.database.MovieDatabase
import com.sdk.movieapp.model.Result
import com.sdk.movieapp.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel: ViewModel() {
    private lateinit var favoriteRepository: FavoriteRepository
    private val _result: MutableLiveData<List<Result>> = MutableLiveData()
    val result: LiveData<List<Result>> get() = _result

    fun init(movieDatabase: MovieDatabase) {
        favoriteRepository = FavoriteRepository(movieDatabase)
        viewModelScope.launch {
            favoriteRepository.getAllMovies().collect {
                _result.postValue(it)
            }
        }
    }
}
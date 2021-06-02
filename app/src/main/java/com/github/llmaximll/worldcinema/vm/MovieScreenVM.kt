package com.github.llmaximll.worldcinema.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.llmaximll.worldcinema.dataclasses.network.MovieInfo
import com.github.llmaximll.worldcinema.repositories.CinemaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieScreenVM : ViewModel() {
    private val repository = CinemaRepository.get()
    private val _movieInfo = MutableStateFlow<MovieInfo?>(null)
    val movieInfo = _movieInfo.asStateFlow()

    fun downloadInfoRequiredMovie(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieInfo.value = repository.downloadInfoRequiredMovie(movieId)
        }
    }
}
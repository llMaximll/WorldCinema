package com.github.llmaximll.worldcinema.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.llmaximll.worldcinema.dataclasses.network.MovieInfo
import com.github.llmaximll.worldcinema.repositories.CinemaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewPagerMainScreenTrendsVM : ViewModel() {
    private val repository = CinemaRepository.get()
    private val _movieInfo = MutableStateFlow<List<MovieInfo>?>(listOf())
    val movieInfo = _movieInfo.asStateFlow()

    fun downloadInfoMovies(filterNumber: Int) {
        val filter = when (filterNumber) {
            0 -> "inTrend"
            1 -> "new"
            2 -> "forMe"
            else -> "new"
        }
        viewModelScope.launch(Dispatchers.IO) {
            _movieInfo.value = repository.downloadInfoMovies(null, filter)
        }
    }
}
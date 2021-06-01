package com.github.llmaximll.worldcinema.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.llmaximll.worldcinema.common.CommonFunctions
import com.github.llmaximll.worldcinema.dataclasses.network.MovieInfo
import com.github.llmaximll.worldcinema.dataclasses.network.PosterInfo
import com.github.llmaximll.worldcinema.repositories.CinemaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainScreenVM : ViewModel() {
    private val repository = CinemaRepository.get()
    //posterInfo
    private val _posterInfo: MutableStateFlow<PosterInfo?> =
        MutableStateFlow(null)
    val posterInfo = _posterInfo.asStateFlow()
    //lastView
    private val _lastViewInfo: MutableStateFlow<MovieInfo?> =
        MutableStateFlow(null)
    val lastViewInfo = _lastViewInfo.asStateFlow()

    fun downloadInfoPoster() {
        viewModelScope.launch(Dispatchers.IO) {
            _posterInfo.value = repository.downloadInfoPoster(null)
        }
    }

    fun downloadInfoLastView(token: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _lastViewInfo.value = repository.downloadInfoLastView(token.toString(), "lastView")
        }
    }
}
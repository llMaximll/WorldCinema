package com.github.llmaximll.worldcinema.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.llmaximll.worldcinema.dataclasses.network.PosterInfo
import com.github.llmaximll.worldcinema.repositories.CinemaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainScreenVM : ViewModel() {
    private val repository = CinemaRepository.get()
    private val _posterInfo: MutableStateFlow<PosterInfo?> =
        MutableStateFlow(PosterInfo("", "", ""))
    val posterInfo = _posterInfo.asStateFlow()

    fun downloadInfoPoster() {
        viewModelScope.launch(Dispatchers.IO) {
            _posterInfo.value = repository.downloadInfoPoster()
        }
    }
}
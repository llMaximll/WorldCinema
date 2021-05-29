package com.github.llmaximll.worldcinema

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine

class LaunchScreenVM() : ViewModel() {

    private val _state = MutableStateFlow(false)
    val state = _state.asStateFlow()

    fun startNextScreen(time: Long) {
        viewModelScope.launch {
            delay(time)
            _state.value = true
        }
    }
}
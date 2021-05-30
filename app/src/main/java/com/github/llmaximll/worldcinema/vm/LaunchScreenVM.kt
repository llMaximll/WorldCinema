package com.github.llmaximll.worldcinema.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.llmaximll.worldcinema.common.CommonFunctions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LaunchScreenVM() : ViewModel() {

    private val _state = MutableStateFlow(false)
    private val cf = CommonFunctions.get()
    val state = _state.asStateFlow()

    fun startNextScreen(time: Long) {
        viewModelScope.launch {
            delay(time)
            _state.value = true
        }
    }

    fun checkFirstLaunch(context: Context?): Boolean {
        val sp = cf.sharedPreferences(context)
        return sp?.getBoolean(cf.spFirstLaunch, false) ?: false
    }
}
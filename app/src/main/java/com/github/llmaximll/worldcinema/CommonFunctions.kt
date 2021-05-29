package com.github.llmaximll.worldcinema

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class CommonFunctions {

    fun log(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }

    fun <T:ViewModel> initVM(viewModelStoreOwner: ViewModelStoreOwner, modelClass: Class<T>): ViewModel {
        return ViewModelProvider(viewModelStoreOwner).get(modelClass)
    }
}
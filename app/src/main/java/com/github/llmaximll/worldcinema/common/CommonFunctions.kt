package com.github.llmaximll.worldcinema.common

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.github.llmaximll.worldcinema.BuildConfig

class CommonFunctions private constructor() {

    private val spName = "sp_worldCinema"
    val spFirstLaunch = "sp_first_launch"

    fun toast(context: Context?, message: String) {
        val toast = Toast.makeText(
            context,
            message,
            Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }

    fun log(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }

    fun <T:ViewModel> initVM(viewModelStoreOwner: ViewModelStoreOwner, modelClass: Class<T>): ViewModel {
        return ViewModelProvider(viewModelStoreOwner).get(modelClass)
    }

    fun sharedPreferences(context: Context?): SharedPreferences? =
        context?.getSharedPreferences(spName, Context.MODE_PRIVATE)

    companion object {
        private var INSTANCE: CommonFunctions? = null

        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = CommonFunctions()
            }
        }

        fun get(): CommonFunctions {
            return requireNotNull(INSTANCE) {
                "CommonFunctions must be initialized"
            }
        }
    }
}
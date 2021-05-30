package com.github.llmaximll.worldcinema

import android.app.Application
import com.github.llmaximll.worldcinema.common.CommonFunctions
import com.github.llmaximll.worldcinema.repositories.CinemaRepository

class WorldCinemaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CommonFunctions.initialize()
        CinemaRepository.initialize(this)
    }
}
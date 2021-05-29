package com.github.llmaximll.worldcinema

import android.app.Application
import com.github.llmaximll.worldcinema.repositories.CinemaRepository

class WorldCinemaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CinemaRepository.initialize(this)
    }
}
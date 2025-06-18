package com.teamsolply.solply

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SolplyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
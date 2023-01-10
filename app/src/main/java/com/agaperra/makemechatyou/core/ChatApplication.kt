package com.agaperra.makemechatyou.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Chat application
 *
 * @constructor Create empty Chat application
 */
@HiltAndroidApp
class ChatApplication : Application() {


    /**
     * On create
     *
     */
    override fun onCreate() {
        super.onCreate()
    }

}
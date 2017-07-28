package com.samuelepontremoli.fsounds.commons

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by s.pontremoli on 10/07/2017.
 * The application class
 * Adds LeakCanary
 */
class FApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

}
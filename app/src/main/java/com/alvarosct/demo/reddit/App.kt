package com.alvarosct.demo.reddit

import android.app.Application
import com.alvarosct.demo.reddit.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        startKoin {
            androidContext(this@App)
            modules(retrofitModule, roomModule, appModule, viewModelsModule)
        }

    }
}
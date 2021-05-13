package com.alvarosct.demo.reddit

import android.app.Application
import com.alvarosct.demo.reddit.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(retrofitModule, roomModule, appModule, viewModelsModule)
        }
    }
}
package com.myapplication


import android.app.Application
import di.appModule
import di.getDatastoreModuleByPlatform
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class App : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, getDatastoreModuleByPlatform())
        }
    }
}
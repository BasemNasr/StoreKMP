package com.myapplication


import android.app.Application
import org.koin.core.component.KoinComponent
class App : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            androidContext(this@App)
//            modules(appModule(this@App))
//        }
    }
}
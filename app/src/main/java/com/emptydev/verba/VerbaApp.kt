package com.emptydev.verba

import android.app.Application
import com.emptydev.verba.di.dataModule
import com.emptydev.verba.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VerbaApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(applicationContext)
            modules(listOf(
                dataModule,
                viewModelModule
            ))
        }
    }
}
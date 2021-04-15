package com.example.zoointro.common

import android.app.Application
import android.content.Context
import com.example.zoointro.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    companion object {
        var appContext: Context? = null
    }

    override fun onCreate() {
        appContext = applicationContext
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    networkModule,
                    viewModelModule,
                    repositoryModule,
                    plantDataSourceFactory
                )
            )
        }
    }
}

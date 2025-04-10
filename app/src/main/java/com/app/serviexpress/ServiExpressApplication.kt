package com.app.serviexpress

import android.app.Application
import com.app.serviexpress.di.authModule
import com.app.serviexpress.di.registerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ServiExpressApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ServiExpressApplication)
            modules(authModule, registerModule)
        }
    }
}
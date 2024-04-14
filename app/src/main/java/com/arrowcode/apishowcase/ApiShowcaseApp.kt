package com.arrowcode.apishowcase

import android.app.Application
import com.arrowcode.data.di.dataModule
import com.arrowcode.data.di.databaseModule
import com.arrowcode.data.di.networkModule
import com.arrowcode.domain.di.domainModule
import com.arrowcode.view.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ApiShowcaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ApiShowcaseApp)
            modules(networkModule, databaseModule, dataModule, domainModule, viewModule)
        }
    }
}
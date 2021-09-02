package com.github.app

import android.app.Application
import com.github.app.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GithubApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GithubApp)
            modules(
                listOf(
                    Modules.dataModel,
                    Modules.domainModel,
                    Modules.useCaseModel,
                    Modules.viewModel
                )
            )
        }

    }
}
package com.hamid.learninggauth

import android.app.Application
import com.hamid.learninggauth.core.data.local.AppDatabase
import com.hamid.learninggauth.core.data.repo.Repository
import com.hamid.learninggauth.core.utils.AppPreferences
import com.hamid.learninggauth.viewmodel.AppViewModel
import com.najva.sdk.NajvaClient
import com.najva.sdk.NajvaConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            factory { AppDatabase.getInstance(this@Application) }
            factory { Repository(get(), this@Application) }
            viewModel { AppViewModel(get()) }
            single { AppPreferences(this@Application) }
        }

        startKoin {
            androidContext(this@Application)
            modules(appModule)
        }


        // najva config
        val config = NajvaConfiguration()
        registerActivityLifecycleCallbacks(NajvaClient.getInstance(this, config))

    }

    override fun onTerminate() {
        super.onTerminate()

        NajvaClient.getInstance().onAppTerminated()
    }
}
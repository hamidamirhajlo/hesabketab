package com.hamid.learninggauth

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.hamid.learninggauth.core.data.local.AppDatabase
import com.hamid.learninggauth.core.data.repo.Repository
import com.hamid.learninggauth.core.utils.AppPreferences
import com.hamid.learninggauth.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            factory { AppDatabase.getInstance(this@Application) }
            factory { Repository(get()) }
            viewModel { AppViewModel(get()) }
            single { AppPreferences(this@Application) }
        }

        startKoin {
            androidContext(this@Application)
            modules(appModule)
        }




    }
}
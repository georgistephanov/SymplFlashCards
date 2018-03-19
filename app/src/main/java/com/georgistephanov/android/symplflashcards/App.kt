package com.georgistephanov.android.symplflashcards

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import com.georgistephanov.android.symplflashcards.di.component.ApplicationComponent
import com.georgistephanov.android.symplflashcards.di.component.DaggerApplicationComponent
import com.georgistephanov.android.symplflashcards.di.module.ApplicationModule
import javax.inject.Inject

class App : Application() {

    lateinit var component: ApplicationComponent
        private set

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        component.inject(this)
    }
}
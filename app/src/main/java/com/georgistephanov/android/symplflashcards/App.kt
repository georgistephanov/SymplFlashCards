package com.georgistephanov.android.symplflashcards

import android.app.Application
import com.georgistephanov.android.symplflashcards.di.component.ApplicationComponent
import com.georgistephanov.android.symplflashcards.di.component.DaggerApplicationComponent
import com.georgistephanov.android.symplflashcards.di.module.ApplicationModule

class App : Application() {

    lateinit var component: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        component.inject(this)
    }
}
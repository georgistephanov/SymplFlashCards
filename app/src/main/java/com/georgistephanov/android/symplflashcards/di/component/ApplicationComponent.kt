package com.georgistephanov.android.symplflashcards.di.component

import android.app.Application
import android.content.Context
import com.georgistephanov.android.symplflashcards.App
import com.georgistephanov.android.symplflashcards.data.DataManager
import com.georgistephanov.android.symplflashcards.di.ApplicationContext
import com.georgistephanov.android.symplflashcards.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: App)

    @ApplicationContext
    fun context() : Context

    fun application() : Application

    fun getDataManager() : DataManager
}
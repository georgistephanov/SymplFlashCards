package com.georgistephanov.android.symplflashcards.di.module

import android.app.Application
import android.content.Context
import com.georgistephanov.android.symplflashcards.data.AppDataManager
import com.georgistephanov.android.symplflashcards.data.DataManager
import com.georgistephanov.android.symplflashcards.data.room.AppDbHelper
import com.georgistephanov.android.symplflashcards.data.room.DbHelper
import com.georgistephanov.android.symplflashcards.di.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    @ApplicationContext
    internal fun provideContext() : Context = mApplication

    @Provides
    internal fun provideApplication() : Application = mApplication

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager) : DataManager = appDataManager

    @Provides
    @Singleton
    internal fun provideDbHelper(appDbHelper: AppDbHelper) : DbHelper = appDbHelper

}

package com.georgistephanov.android.symplflashcards.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.georgistephanov.android.symplflashcards.di.ActivityContext
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val mActivity: AppCompatActivity) {

    @Provides
    @ActivityContext
    internal fun provideContext() : Context = mActivity

    @Provides
    internal fun provideActivity() : AppCompatActivity = mActivity

}
package com.georgistephanov.android.symplflashcards.di.component

import android.support.v7.app.AppCompatActivity
import com.georgistephanov.android.symplflashcards.di.PerActivity
import com.georgistephanov.android.symplflashcards.di.module.ActivityModule
import dagger.Component

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: AppCompatActivity)
}
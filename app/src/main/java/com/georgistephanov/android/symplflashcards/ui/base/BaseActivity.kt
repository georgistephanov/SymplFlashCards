package com.georgistephanov.android.symplflashcards.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.georgistephanov.android.symplflashcards.App
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.di.component.ActivityComponent
import com.georgistephanov.android.symplflashcards.di.component.DaggerActivityComponent
import com.georgistephanov.android.symplflashcards.di.module.ActivityModule
import org.jetbrains.anko.find
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {

    private val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent((application as App).component)
                .build()
    }

    val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setSupportActionBar(toolbar)

        activityComponent.inject(this)
    }
}
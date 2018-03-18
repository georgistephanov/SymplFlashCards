package com.georgistephanov.android.symplflashcards.ui.base

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

open class BaseActivity : AppCompatActivity() {

    private val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent((application as App).component)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setSupportActionBar(find<Toolbar>(R.id.toolbar))

        activityComponent.inject(this)
    }
}
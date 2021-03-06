package com.georgistephanov.android.symplflashcards.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.georgistephanov.android.symplflashcards.App
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.di.component.ActivityComponent
import com.georgistephanov.android.symplflashcards.di.component.DaggerActivityComponent
import com.georgistephanov.android.symplflashcards.di.module.ActivityModule
import org.jetbrains.anko.find

abstract class BaseActivity : AppCompatActivity() {

    private val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent((application as App).component)
                .build()
    }

    val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        activityComponent.inject(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
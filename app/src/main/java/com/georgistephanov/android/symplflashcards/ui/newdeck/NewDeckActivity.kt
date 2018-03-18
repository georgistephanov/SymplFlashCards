package com.georgistephanov.android.symplflashcards.ui.newdeck

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.ui.base.BaseActivity

class NewDeckActivity : BaseActivity() {

    private val model: NewDeckViewModel by lazy { ViewModelProviders.of(this).get(NewDeckViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_deck)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        toolbar.title = resources.getString(R.string.new_deck_activity)
        toolbar.title
    }
}

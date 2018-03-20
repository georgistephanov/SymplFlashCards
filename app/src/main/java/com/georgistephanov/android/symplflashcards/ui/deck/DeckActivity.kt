package com.georgistephanov.android.symplflashcards.ui.deck

import android.os.Bundle
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.ui.base.BaseActivity

class DeckActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        toolbar.title = resources.getString(R.string.deck_activity)
    }
}

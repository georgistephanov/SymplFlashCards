package com.georgistephanov.android.symplflashcards.ui.deck

import android.os.Bundle
import android.view.Menu
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.ui.base.BaseActivity

class DeckActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = intent.extras.get("deckName") as String
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.deck, menu)
        return true
    }
}

package com.georgistephanov.android.symplflashcards.ui.deck

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.Menu
import com.georgistephanov.android.symplflashcards.App
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_deck.*

class DeckActivity : BaseActivity(), DeckListFragment.OnListFragmentInteractionListener {

    private val model by lazy {
        ViewModelProviders
                .of(this, (application as App).viewModelFactory)
                .get(DeckViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck)

        val deckName = intent.extras.get("deckName") as String
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = deckName
        }

        fab_deck.setOnClickListener { _ ->
            if (model.isAddCardButtonLocked) {
                return@setOnClickListener
            }
            model.createCard()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.deck, menu)
        return true
    }

    override fun onCardContentChanged(cardId: Int, front: String, back: String) {
        model.updateCard(cardId, front, back)
    }

    override fun onCardDeleted(cardId: Int) {
        model.deleteCard(cardId)
    }
}

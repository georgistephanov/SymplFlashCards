package com.georgistephanov.android.symplflashcards.ui.deck

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.georgistephanov.android.symplflashcards.App
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.ui.AutoResizeTextView
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

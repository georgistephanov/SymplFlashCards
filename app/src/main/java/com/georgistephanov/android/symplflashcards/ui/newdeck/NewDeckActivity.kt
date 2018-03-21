package com.georgistephanov.android.symplflashcards.ui.newdeck

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.georgistephanov.android.symplflashcards.App
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.data.DataManager
import com.georgistephanov.android.symplflashcards.data.room.entities.Deck
import com.georgistephanov.android.symplflashcards.ui.base.BaseActivity
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.find

class NewDeckActivity : BaseActivity() {

    private val etDeckName by lazy { find<EditText>(R.id.et_deck_name) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_deck)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = resources.getString(R.string.new_deck_activity)
        }
    }

    fun onCreateDeckClick(@Suppress("UNUSED_PARAMETER") view: View) {
        val dataManager: DataManager = (application as App).component.getDataManager()
        val deckName = etDeckName.text.toString()

        launch {
            if (deckName.isEmpty()) {
                runOnUiThread {
                    Toast.makeText(this@NewDeckActivity, resources.getString(R.string.create_deck_empty_name), Toast.LENGTH_SHORT).show()
                }
            } else if ( dataManager.getDeck(deckName) != null ) {
                runOnUiThread {
                    Toast.makeText(this@NewDeckActivity, resources.getString(R.string.create_deck_name_exists), Toast.LENGTH_SHORT).show()
                }
            } else {
                dataManager.insertDeck(Deck(deckName))
                runOnUiThread {
                    Toast.makeText(this@NewDeckActivity, "Successfully created the deck", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }
        }
    }
}

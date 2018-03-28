package com.georgistephanov.android.symplflashcards.ui.deck

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.support.v4.content.ContextCompat
import com.georgistephanov.android.symplflashcards.App
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.data.room.entities.DeckAndCards
import com.georgistephanov.android.symplflashcards.data.room.entities.FlashCard
import com.georgistephanov.android.symplflashcards.di.ApplicationContext
import kotlinx.coroutines.experimental.async
import javax.inject.Inject


class DeckViewModel @Inject constructor(@ApplicationContext val applicationContext: Context) : ViewModel() {

    private val dataManager = (applicationContext as App).component.getDataManager()

    private lateinit var deckName: String
    lateinit var deck: LiveData<DeckAndCards>

    fun setDeckName(name: String) {
        deckName = name
        deck = dataManager.getDeck(deckName)
    }

    fun createCard() {
        async {
            dataManager.insertFlashCard(
                    FlashCard(
                        front = applicationContext.resources.getString(R.string.card_front_default_text),
                        back = applicationContext.resources.getString(R.string.card_back_default_text),
                        label = "",
                        color = ContextCompat.getColor(applicationContext, R.color.cardTint),
                        deckName = deckName
                    )
            )
        }
    }

    fun updateCard(_id: Int, front: String = "", back: String = "") {
        async {
            val card = dataManager.getFlashCard(_id)

            card ?: return@async

            if (front.isNotEmpty()) {
                card.front = front
            } else if (back.isNotEmpty()) {
                card.back = back
            }

            dataManager.updateFlashCard(card)
        }
    }
}
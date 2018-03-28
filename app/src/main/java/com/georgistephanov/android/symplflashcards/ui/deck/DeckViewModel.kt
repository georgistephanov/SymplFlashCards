package com.georgistephanov.android.symplflashcards.ui.deck

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
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

    lateinit var deck: LiveData<DeckAndCards>
    val card: MutableLiveData<FlashCard> = MutableLiveData()

    fun setDeckName(deckName: String) {
        async {
            deck = dataManager.getDeck(deckName)

            if (deck.value!!.cards.isEmpty()) {
                dataManager.insertFlashCard( FlashCard(
                        front = applicationContext.resources.getString(R.string.card_front_default_text),
                        back = applicationContext.resources.getString(R.string.card_back_default_text),
                        label = "",
                        color = ContextCompat.getColor(applicationContext, R.color.cardTint),
                        deckName = deckName )
                )
            }

            card.value = deck.value!!.cards[0]
        }
    }

    fun updateCard(front: String = "", back: String = "") {
        dataManager.updateFlashCard(
                FlashCard(
                        card.value!!._id,
                        if (front.isNotEmpty()) front else card.value!!.front,
                        if (back.isNotEmpty()) back else card.value!!.back,
                        card.value!!.label,
                        card.value!!.color,
                        card.value!!.deckName
                )
        )
    }
}
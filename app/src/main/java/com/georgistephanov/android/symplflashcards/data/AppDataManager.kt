package com.georgistephanov.android.symplflashcards.data

import android.arch.lifecycle.LiveData
import com.georgistephanov.android.symplflashcards.data.room.DbHelper
import com.georgistephanov.android.symplflashcards.data.room.entities.Deck
import com.georgistephanov.android.symplflashcards.data.room.entities.DeckAndCards
import com.georgistephanov.android.symplflashcards.data.room.entities.FlashCard
import javax.inject.Inject

class AppDataManager @Inject constructor(private val appDbHelper: DbHelper) : DataManager {

    override fun getFlashCard(id: Int): FlashCard? = appDbHelper.getFlashCard(id)

    override fun getAllFlashCardsFromDeck(deckName: String): List<FlashCard>
            = appDbHelper.getAllFlashCardsFromDeck(deckName)

    override fun insertFlashCard(flashCard: FlashCard) {
        appDbHelper.insertFlashCard(flashCard)
    }

    override fun deleteFlashCard(flashCard: FlashCard) {
        appDbHelper.deleteFlashCard(flashCard)
    }

    override fun deleteFlashCard(id: Int) {
        appDbHelper.deleteFlashCard(id)
    }

    override fun getAllDecks(): LiveData<List<DeckAndCards>> = appDbHelper.getAllDecks()

    override fun getDeck(name: String): DeckAndCards? = appDbHelper.getDeck(name)

    override fun insertDeck(deck: Deck) {
        appDbHelper.insertDeck(deck)
    }

    override fun deleteDeck(deck: Deck) {
        appDbHelper.deleteDeck(deck)
    }

    override fun deleteDeck(name: String) {
        appDbHelper.deleteDeck(name)
    }

}
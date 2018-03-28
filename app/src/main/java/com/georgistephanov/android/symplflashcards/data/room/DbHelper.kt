package com.georgistephanov.android.symplflashcards.data.room

import android.arch.lifecycle.LiveData
import com.georgistephanov.android.symplflashcards.data.room.entities.Deck
import com.georgistephanov.android.symplflashcards.data.room.entities.DeckAndCards
import com.georgistephanov.android.symplflashcards.data.room.entities.FlashCard

interface DbHelper {

    /* Flash card methods */
    fun getFlashCard(id: Int) : FlashCard?
    fun getAllFlashCardsFromDeck(deckName: String) : LiveData<List<FlashCard>>
    fun insertFlashCard(flashCard: FlashCard)
    fun updateFlashCard(flashCard: FlashCard)
    fun deleteFlashCard(flashCard: FlashCard)
    fun deleteFlashCard(id: Int)

    /* Deck methods */
    fun getAllDecks() : LiveData<List<DeckAndCards>>
    fun getDeck(name: String) : LiveData<DeckAndCards>
    fun insertDeck(deck: Deck)
    fun deleteDeck(deck: Deck)
    fun deleteDeck(name: String)

}
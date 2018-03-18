package com.georgistephanov.android.symplflashcards.data.room

import com.georgistephanov.android.symplflashcards.data.room.entities.Deck
import com.georgistephanov.android.symplflashcards.data.room.entities.FlashCard

interface DbHelper {

    /* Flash card methods */
    fun getFlashCard(id: Int) : FlashCard
    fun insertFlashCard(flashCard: FlashCard)
    fun deleteFlashCard(flashCard: FlashCard)
    fun deleteFlashCard(id: Int)

    /* Deck methods */
    fun getDeck(id: Int) : Deck
    fun insertDeck(deck: Deck)
    fun deleteDeck(deck: Deck)
    fun deleteDeck(id: Int)

}